import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISystemParameter, SystemParameter } from 'app/shared/model/system-parameter.model';
import { SystemParameterService } from './system-parameter.service';
import { SystemParameterComponent } from './system-parameter.component';
import { SystemParameterDetailComponent } from './system-parameter-detail.component';
import { SystemParameterUpdateComponent } from 'app/entities/system-parameter/system-parameter-update.component';

@Injectable({ providedIn: 'root' })
export class SystemParameterResolve implements Resolve<ISystemParameter> {
  constructor(private service: SystemParameterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISystemParameter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((systemParameter: HttpResponse<SystemParameter>) => {
          if (systemParameter.body) {
            return of(systemParameter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SystemParameter());
  }
}

export const systemParameterRoute: Routes = [
  {
    path: '',
    component: SystemParameterComponent,
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'arrestControlApp.systemParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SystemParameterDetailComponent,
    resolve: {
      systemParameter: SystemParameterResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'arrestControlApp.systemParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SystemParameterUpdateComponent,
    resolve: {
      systemParameter: SystemParameterResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'arrestControlApp.systemParameter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
