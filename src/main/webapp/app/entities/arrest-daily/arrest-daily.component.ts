import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable, timer } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IArrest } from 'app/shared/model/arrest.model';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { ROLE_ADMIN, ROLE_REPORT, ROLE_UNIT, ROLE_USER } from 'app/shared/constants/role.constants';
import { IUnit } from 'app/shared/model/unit.model';
import { OfficeService } from 'app/entities/office/office.service';
import { UnitService } from 'app/entities/unit/unit.service';
import { IOffice } from 'app/shared/model/office.model';
import { ArrestDailyService } from 'app/entities/arrest-daily/arrest-daily.service';
import { HttpResponse } from '@angular/common/http';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import * as moment from 'moment';
import { FormBuilder, Validators } from '@angular/forms';
import { ArrestDaily, IArrestDaily } from 'app/shared/model/arrest-daily.model';

type SelectableEntity = IUnit | IOffice;

@Component({
  selector: 'jhi-arrest-daily',
  templateUrl: './arrest-daily.component.html'
})
export class ArrestDailyComponent implements OnInit, OnDestroy {
  private subscription?: Subscription;

  currentAccount: Account | null = null;
  arrests?: IArrest[];
  eventSubscriber?: Subscription;
  totalItems = 0;

  units?: IUnit[];
  offices?: IOffice[];

  datePrinting?: moment.Moment;

  editForm = this.fb.group({
    arrestDate: [null, [Validators.required]],
    unitId: [],
    officeId: []
  });

  userAdmin = false;
  userReport = false;
  userUnit = false;
  userUser = false;

  constructor(
    protected accountService: AccountService,
    protected officeService: OfficeService,
    protected unitService: UnitService,
    protected service: ArrestDailyService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder
  ) {}

  loadAll(): void {
    const arrestDaily = this.createFromForm();
    this.service
      .query({
        'unitId.equals': arrestDaily.unitId,
        'officeId.equals': arrestDaily.officeId,
        'arrestDate.greaterThanOrEqual': arrestDaily.arrestDate ? arrestDaily.arrestDate.format(DATE_FORMAT) + 'T00:00:00.000Z' : undefined,
        'arrestDate.lessThanOrEqual': arrestDaily.arrestDate ? arrestDaily.arrestDate.format(DATE_FORMAT) + 'T23:59:59.996Z' : undefined
      })
      .subscribe(
        (res: HttpResponse<IArrest[]>) => this.onSuccess(res.body),
        () => this.onError()
      );
  }

  clearValues(): void {
    this.updateForm(this.createArrestDaily());
    this.loadAll();
  }

  createArrestDaily(): IArrestDaily {
    const arrestDaily = new ArrestDaily();
    arrestDaily.arrestDate = moment();
    arrestDaily.unitId = this.loadUnit();
    arrestDaily.officeId = undefined;
    return arrestDaily;
  }

  updateForm(arrestDaily: IArrestDaily): void {
    this.editForm.patchValue({
      arrestDate: arrestDaily.arrestDate,
      unitId: arrestDaily.unitId,
      officeId: arrestDaily.officeId
    });
  }

  private createFromForm(): IArrestDaily {
    return {
      ...new ArrestDaily(),
      arrestDate: this.editForm.get(['arrestDate'])!.value,
      unitId: this.editForm.get(['unitId'])!.value,
      officeId: this.editForm.get(['officeId'])!.value
    };
  }

  ngOnInit(): void {
    this.datePrinting = moment();
    this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
    this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
    this.userReport = this.accountService.hasAnyAuthority(ROLE_REPORT);
    this.userUnit = this.accountService.hasAnyAuthority(ROLE_UNIT);
    this.userUser = this.accountService.hasAnyAuthority(ROLE_USER);
    this.accountService.identity().subscribe(
      value => {
        this.currentAccount = value;
      },
      () => {},
      () => {
        this.updateForm(this.createArrestDaily());
        this.loadAll();
        if (this.loadUnit()) {
          this.officeService
            .query({
              'unitId.equals': this.loadUnit()
            })
            .subscribe((res: HttpResponse<IUnit[]>) => (this.offices = res.body || []));
        }
      }
    );

    this.onChanges();
    this.registerChangeInArrests();
    this.initTimer();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
    if (this.subscription) this.subscription.unsubscribe();
  }

  trackId(index: number, item: IArrest): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInArrests(): void {
    this.eventSubscriber = this.eventManager.subscribe('arrestListModification', () => this.loadAll());
  }

  protected onSuccess(data: IArrest[] | null): void {
    this.arrests = data || [];
  }

  protected onError(): void {}

  eventHandler(): void {
    this.loadAll();
  }

  private loadUnit(): number | undefined {
    if (this.userAdmin || this.userReport) {
      return undefined;
    } else {
      if (this.currentAccount && this.currentAccount.office && this.currentAccount.office.unit && this.currentAccount.office.unit.id) {
        return this.currentAccount.office.unit.id;
      } else return undefined;
    }
  }

  viewUnit(): boolean {
    return this.userAdmin || this.userReport;
  }

  onChanges(): void {
    this.editForm.get(['unitId'])!.valueChanges.subscribe(value => {
      const unitId: number = value;
      if (unitId) {
        this.officeService
          .query({
            'unitId.equals': unitId
          })
          .subscribe((res: HttpResponse<IUnit[]>) => (this.offices = res.body || []));
      }
      this.editForm.patchValue({
        officeId: ''
      });
    });
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  initTimer(): void {
    const obsTimer: Observable<number> = timer(1000, 1000);
    this.subscription = obsTimer.subscribe(
      () => {
        this.datePrinting = moment();
      },
      () => {}
    );
  }
}
