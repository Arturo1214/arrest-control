import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TotalArrestService } from './total.arrest.service';
import { ITotalArrest } from 'app/shared/model/total.arrest.model';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import * as moment from 'moment';

@Component({
  selector: 'jhi-arrest-total',
  templateUrl: './total.arrest.component.html'
})
export class TotalArrestComponent implements OnInit, OnDestroy {
  arrests?: ITotalArrest[];
  eventSubscriber?: Subscription;

  startDate?: moment.Moment = moment();
  endDate?: moment.Moment = moment();
  showDetail = false;

  totalArrested = 0;
  totalPedestrian = 0;
  totalPassenger = 0;
  totalDriver = 0;
  totalMotorized = 0;
  totalVehicle = 0;
  totalMotorcycle = 0;

  constructor(protected totalArrestService: TotalArrestService, protected eventManager: JhiEventManager) {}

  loadAll(): void {
    this.totalArrestService
      .query({
        'arrestDate.greaterThanOrEqual': this.startDate ? this.startDate.format(DATE_FORMAT) + 'T00:00:00.000Z' : undefined,
        'arrestDate.lessThanOrEqual': this.endDate ? this.endDate.format(DATE_FORMAT) + 'T23:59:59.996Z' : undefined
      })
      .subscribe((res: HttpResponse<ITotalArrest[]>) => {
        this.arrests = res.body || [];
        this.totalArrested = 0;
        this.totalPedestrian = 0;
        this.totalPassenger = 0;
        this.totalDriver = 0;
        this.totalMotorized = 0;
        this.totalVehicle = 0;
        this.totalMotorcycle = 0;
        this.arrests.forEach(value => {
          this.totalArrested = this.totalArrested + (value.totalArrested ? value.totalArrested : 0);
          this.totalPedestrian = this.totalPedestrian + (value.totalPedestrian ? value.totalPedestrian : 0);
          this.totalPassenger = this.totalPassenger + (value.totalPassenger ? value.totalPassenger : 0);
          this.totalDriver = this.totalDriver + (value.totalDriver ? value.totalDriver : 0);
          this.totalMotorized = this.totalMotorized + (value.totalMotorized ? value.totalMotorized : 0);
          this.totalVehicle = this.totalVehicle + (value.totalVehicle ? value.totalVehicle : 0);
          this.totalMotorcycle = this.totalMotorcycle + (value.totalMotorcycle ? value.totalMotorcycle : 0);
        });
      });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInArrests();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  registerChangeInArrests(): void {
    this.eventSubscriber = this.eventManager.subscribe('arrestListModification', () => this.loadAll());
  }

  clearSearch(): void {
    this.startDate = undefined;
    this.endDate = undefined;
    this.loadAll();
  }

  search(): void {
    this.loadAll();
  }
}
