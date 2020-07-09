import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { UserService } from 'app/core/user/user.service';
import { User } from 'app/core/user/user.model';
import { UserManagementDeleteDialogComponent } from './user-management-delete-dialog.component';
import { ROLE_ADMIN, ROLE_UNIT } from 'app/shared/constants/role.constants';
import { IUnit } from 'app/shared/model/unit.model';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office/office.service';
import { UnitService } from 'app/entities/unit/unit.service';
import { UserManagementResetPasswordDialogComponent } from 'app/admin/user-management/user-management-reset-password-dialog.component';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './user-management.component.html'
})
export class UserManagementComponent implements OnInit, OnDestroy {
  currentAccount: Account | null = null;
  users: User[] | null = null;
  userListSubscription?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  previousPage!: number;
  ascending!: boolean;
  userAdmin = false;
  userUnit = false;

  units?: IUnit[];
  offices?: IOffice[];

  filter = {
    id: '',
    login: '',
    fullName: '',
    telephone: '',
    email: '',
    unitId: '',
    officeId: ''
  };

  constructor(
    private userService: UserService,
    protected officeService: OfficeService,
    protected unitService: UnitService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private eventManager: JhiEventManager,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data
      .pipe(
        flatMap(
          () => this.accountService.identity(),
          (data, account) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.ascending = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
            this.currentAccount = account;

            this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
            this.userUnit = this.accountService.hasAnyAuthority(ROLE_UNIT);

            this.unitService
              .query({
                'id.equals': this.userAdmin
                  ? ''
                  : this.currentAccount && this.currentAccount.office && this.currentAccount.office.unit
                  ? this.currentAccount.office.unit.id
                  : ''
              })
              .subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));

            this.loadAll();
            this.userListSubscription = this.eventManager.subscribe('userListModification', () => this.loadAll());
          }
        )
      )
      .subscribe();
  }

  ngOnDestroy(): void {
    if (this.userListSubscription) {
      this.eventManager.destroy(this.userListSubscription);
    }
  }

  setActive(user: User, isActivated: boolean): void {
    this.userService.update({ ...user, activated: isActivated }).subscribe(() => this.loadAll());
  }

  trackIdentity(index: number, item: User): any {
    return item.id;
  }

  loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  deleteUser(user: User): void {
    const modalRef = this.modalService.open(UserManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
  }

  resetPasswordUser(user: User): void {
    const modalRef = this.modalService.open(UserManagementResetPasswordDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
  }

  private loadAll(): void {
    let req: any;

    if (this.currentAccount) {
      if (this.currentAccount.authorities) {
        if (!this.userAdmin) {
          this.filter.unitId =
            this.currentAccount.office && this.currentAccount.office.unit && this.currentAccount.office.unit.id
              ? this.currentAccount.office.unit.id.toString()
              : '';
        }

        req = {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'id.equals': this.filter.id,
          'login.contains': this.filter.login,
          'fullName.contains': this.filter.fullName,
          'telephone.contains': this.filter.telephone,
          'email.contains': this.filter.email,
          'unitId.equals': this.filter.unitId,
          'officeId.equals': this.filter.officeId
        };

        this.userService.query(req).subscribe((res: HttpResponse<User[]>) => this.onSuccess(res.body, res.headers));
      }
    }
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(users: User[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
  }

  eventHandler(): void {
    this.loadAll();
  }

  eventUnitHandler(): void {
    this.filter.officeId = '';
    if (this.filter.unitId) {
      this.officeService
        .query({
          'unitId.equals': this.filter.unitId
        })
        .subscribe((res: HttpResponse<IUnit[]>) => (this.offices = res.body || []));
    } else {
      this.offices = [];
    }

    this.loadAll();
  }
}
