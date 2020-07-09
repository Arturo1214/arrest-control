import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IArrest } from 'app/shared/model/arrest.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ArrestStatusNoFineDialogComponent } from './arrest-status-no-fine-dialog.component';
import { ArrestService } from 'app/entities/arrest/arrest.service';
import { ArrestStatusPaidOutDialogComponent } from 'app/entities/arrest-status/arrest-status-paid-out-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { ROLE_ADMIN, ROLE_UNIT, ROLE_USER } from 'app/shared/constants/role.constants';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { IUnit } from 'app/shared/model/unit.model';
import { OfficeService } from 'app/entities/office/office.service';
import { UnitService } from 'app/entities/unit/unit.service';
import { IOffice } from 'app/shared/model/office.model';
import { ArrestStatusPendingDialogComponent } from 'app/entities/arrest-status/arrest-status-pending-dialog.component';

@Component({
  selector: 'jhi-arrest-status',
  templateUrl: './arrest-status.component.html'
})
export class ArrestStatusComponent implements OnInit, OnDestroy {
  currentAccount: Account | null = null;
  arrests?: IArrest[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  units?: IUnit[];
  offices?: IOffice[];

  filter = {
    id: '',
    documentNumber: '',
    fullName: '',
    description: '',
    type: '',
    vehicleType: '',
    plate: '',
    userLogin: '',
    unitId: '',
    officeId: '',
    status: PaymentStatus.PENDING,
    depositNumber: '',
    stateDescription: ''
  };

  userAdmin = false;
  userUnit = false;
  userUser = false;

  constructor(
    protected accountService: AccountService,
    protected officeService: OfficeService,
    protected unitService: UnitService,
    protected service: ArrestService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.service
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'id.equals': this.filter.id,
        'documentNumber.contains': this.filter.documentNumber,
        'fullName.contains': this.filter.fullName,
        'description.contains': this.filter.description,
        'type.equals': this.filter.type,
        'vehicleType.equals': this.filter.vehicleType,
        'plate.contains': this.filter.plate,
        'userLogin.equals': this.loadUser(),
        'unitId.equals': this.loadUnit(),
        'officeId.equals': this.filter.officeId,
        'status.equals': this.filter.status,
        'depositNumber.contains': this.filter.depositNumber,
        'stateDescription.contains': this.filter.stateDescription
      })
      .subscribe(
        (res: HttpResponse<IArrest[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;

      this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
      this.userUnit = this.accountService.hasAnyAuthority(ROLE_UNIT);
      this.userUser = this.accountService.hasAnyAuthority(ROLE_USER);
      this.accountService.identity().subscribe(
        value => {
          this.currentAccount = value;
        },
        () => {},
        () => {
          this.filter.unitId = this.loadUnit();
          this.eventUnitHandler();
          this.loadPage();
        }
      );
    });

    this.registerChangeInArrests();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IArrest): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInArrests(): void {
    this.eventSubscriber = this.eventManager.subscribe('arrestListModification', () => this.loadPage());
  }

  arrestNoFine(arrest: IArrest): void {
    const modalRef = this.modalService.open(ArrestStatusNoFineDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.arrest = arrest;
  }

  arrestPaidOut(arrest: IArrest): void {
    const modalRef = this.modalService.open(ArrestStatusPaidOutDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.arrest = arrest;
  }

  arrestPending(arrest: IArrest): void {
    const modalRef = this.modalService.open(ArrestStatusPendingDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.arrest = arrest;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IArrest[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/arrest-status'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.arrests = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  eventHandler(): void {
    this.loadPage();
  }

  arrestView(arrest: IArrest): boolean {
    if (arrest.status === PaymentStatus.PENDING) {
      if (this.userAdmin) return true;
      else {
        if (
          arrest.office &&
          arrest.office.unit &&
          this.currentAccount &&
          this.currentAccount.office &&
          this.currentAccount.office.unit &&
          arrest.office.unit.id === this.currentAccount.office.unit.id
        ) {
          return true;
        } else {
          return false;
        }
      }
    } else {
      return false;
    }
  }

  arrestViewPending(arrest: IArrest): boolean {
    if (arrest.status !== PaymentStatus.PENDING) {
      if (this.userAdmin) return true;
      else {
        return false;
      }
    } else {
      return false;
    }
  }

  private loadUnit(): string {
    if (this.userAdmin) {
      return this.filter.unitId.toString();
    } else {
      if (this.currentAccount && this.currentAccount.office && this.currentAccount.office.unit && this.currentAccount.office.unit.id) {
        return this.currentAccount.office.unit.id.toString();
      } else return '';
    }
  }

  private loadUser(): string {
    if (!this.userAdmin && !this.userUnit) {
      if (this.currentAccount && this.currentAccount.login) return this.currentAccount.login;
      else return this.filter.userLogin;
    } else {
      return this.filter.userLogin;
    }
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

    this.loadPage();
  }
}
