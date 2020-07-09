import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeCase } from 'app/shared/model/type-case.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeCaseService } from './type-case.service';
import { TypeCaseDeleteDialogComponent } from './type-case-delete-dialog.component';

@Component({
  selector: 'jhi-type-case',
  templateUrl: './type-case.component.html'
})
export class TypeCaseComponent implements OnInit, OnDestroy {
  typeCases?: ITypeCase[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected typeCaseService: TypeCaseService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.typeCaseService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ITypeCase[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInTypeCases();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeCase): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeCases(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeCaseListModification', () => this.loadPage());
  }

  delete(typeCase: ITypeCase): void {
    const modalRef = this.modalService.open(TypeCaseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeCase = typeCase;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ITypeCase[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/type-case'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.typeCases = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
