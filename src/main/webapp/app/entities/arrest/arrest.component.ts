import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IArrest } from 'app/shared/model/arrest.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ArrestService } from './arrest.service';
import { ArrestDeleteDialogComponent } from './arrest-delete-dialog.component';
import { FormBuilder } from '@angular/forms';
import { IUnit } from 'app/shared/model/unit.model';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office/office.service';
import { UnitService } from 'app/entities/unit/unit.service';
import { ROLE_ADMIN, ROLE_UNIT } from 'app/shared/constants/role.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-arrest',
  templateUrl: './arrest.component.html'
})
export class ArrestComponent implements OnInit, OnDestroy {
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

  userAdmin = false;
  userUnit = false;
  showDetail = false;

  filter = {
    id: '',
    documentNumber: '',
    fullName: '',
    description: '',
    type: '',
    vehicleType: '',
    plate: '',
    userFullName: '',
    unitId: '',
    officeId: '',
    status: '',
    depositNumber: '',
    stateDescription: ''
  };

  constructor(
    protected accountService: AccountService,
    protected officeService: OfficeService,
    protected unitService: UnitService,
    protected arrestService: ArrestService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private fb: FormBuilder
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.arrestService
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
        'userFullName.contains': this.filter.userFullName,
        'unitId.equals': this.filter.unitId,
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
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;

      this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
      this.userUnit = this.accountService.hasAnyAuthority(ROLE_UNIT);
      this.accountService.identity().subscribe(value => (this.currentAccount = value));

      this.loadPage();
    });
    this.registerChangeInArrests();
    this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
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

  delete(arrest: IArrest): void {
    const modalRef = this.modalService.open(ArrestDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
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
    this.router.navigate(['/arrest'], {
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

  viewEdit(unit: IUnit): boolean {
    if (!this.userAdmin && !this.userUnit) {
      return false;
    } else {
      if (this.userAdmin) {
        return true;
      } else {
        if (this.currentAccount) {
          if (this.currentAccount.office && this.currentAccount.office.unit) {
            if (this.currentAccount.office.unit.id === unit.id) {
              return true;
            }
          }
        }
        return false;
      }
    }
  }
}
