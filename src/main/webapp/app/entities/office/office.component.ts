import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOffice } from 'app/shared/model/office.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OfficeService } from './office.service';
import { OfficeDeleteDialogComponent } from './office-delete-dialog.component';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';

@Component({
  selector: 'jhi-office',
  templateUrl: './office.component.html'
})
export class OfficeComponent implements OnInit, OnDestroy {
  offices?: IOffice[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  units?: IUnit[];

  filter = {
    id: '',
    name: '',
    unitId: ''
  };

  constructor(
    protected unitService: UnitService,
    protected officeService: OfficeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));

    this.officeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'id.equals': this.filter.id,
        'name.contains': this.filter.name,
        'unitId.equals': this.filter.unitId
      })
      .subscribe(
        (res: HttpResponse<IOffice[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInOffices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOffice): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOffices(): void {
    this.eventSubscriber = this.eventManager.subscribe('officeListModification', () => this.loadPage());
  }

  delete(office: IOffice): void {
    const modalRef = this.modalService.open(OfficeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.office = office;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IOffice[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/office'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.offices = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  eventHandler(): void {
    this.loadPage();
  }
}
