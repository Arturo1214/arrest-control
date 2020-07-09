import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable, timer } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CheckCase, IRegisterCase } from 'app/shared/model/register-case.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RegisterCaseService } from './register-case.service';
import { RegisterCaseDeleteDialogComponent } from './register-case-delete-dialog.component';
import { RegisterCaseDispacthDialogComponent } from 'app/entities/register-case/register-case-dispatch-dialog.component';
import { RegisterCaseFinalizeDialogComponent } from 'app/entities/register-case/register-case-finalize-dialog.component';
import { faCheck, faUnlock } from '@fortawesome/free-solid-svg-icons';
import { StateRegister } from 'app/shared/model/enumerations/state-register.model';
import { AccountService } from 'app/core/auth/account.service';
import { ROLE_ADMIN } from 'app/shared/constants/role.constants';

@Component({
  selector: 'jhi-register-case',
  templateUrl: './register-case.component.html'
})
export class RegisterCaseComponent implements OnInit, OnDestroy {
  private subscription?: Subscription;
  registerCases?: IRegisterCase[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  faCheck = faCheck;
  faUnlock = faUnlock;
  notAssigned = StateRegister.NOT_ASSIGNED;
  pending = StateRegister.PENDING;

  userAdmin = false;

  constructor(
    protected accountService: AccountService,
    protected registerCaseService: RegisterCaseService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.registerCaseService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'state.in': this.userAdmin ? '' : ['NOT_ASSIGNED', 'PENDING']
      })
      .subscribe(
        (res: HttpResponse<IRegisterCase[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInRegisterCases();
    this.initTimer();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
    if (this.subscription) this.subscription.unsubscribe();
  }

  trackId(index: number, item: IRegisterCase): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRegisterCases(): void {
    this.eventSubscriber = this.eventManager.subscribe('registerCaseListModification', () => this.loadPage());
  }

  delete(registerCase: IRegisterCase): void {
    const modalRef = this.modalService.open(RegisterCaseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.registerCase = registerCase;
  }

  dispatch(registerCase: IRegisterCase): void {
    const modalRef = this.modalService.open(RegisterCaseDispacthDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.registerCase = registerCase;
  }

  finalize(registerCase: IRegisterCase): void {
    const modalRef = this.modalService.open(RegisterCaseFinalizeDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.registerCase = registerCase;
  }

  check(id: number): void {
    const checkCase = new CheckCase(id);
    this.registerCaseService.check(checkCase).subscribe(() => {
      this.eventManager.broadcast('registerCaseListModification');
    });
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IRegisterCase[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/register-case'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.registerCases = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  viewEdit(state: StateRegister): boolean {
    return this.notAssigned === state || this.pending === state;
  }

  viewNotAssigned(state: StateRegister): boolean {
    return this.notAssigned === state;
  }

  viewPending(state: StateRegister): boolean {
    return this.pending === state;
  }

  viewCheck(state: StateRegister): boolean {
    return this.pending === state;
  }

  initTimer(): void {
    const obsTimer: Observable<number> = timer(3000, 3000);
    this.subscription = obsTimer.subscribe(
      () => {
        this.eventManager.broadcast('registerCaseListModification');
      },
      () => {}
    );
  }
}
