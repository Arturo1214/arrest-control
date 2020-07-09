import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRegisterCase, RegisterCase } from 'app/shared/model/register-case.model';
import { RegisterCaseService } from './register-case.service';
import { RegisterCaseComponent } from './register-case.component';
import { RegisterCaseDetailComponent } from './register-case-detail.component';
import { RegisterCaseUpdateComponent } from './register-case-update.component';

@Injectable({ providedIn: 'root' })
export class RegisterCaseResolve implements Resolve<IRegisterCase> {
  constructor(private service: RegisterCaseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegisterCase> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((registerCase: HttpResponse<RegisterCase>) => {
          if (registerCase.body) {
            return of(registerCase.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RegisterCase());
  }
}

export const registerCaseRoute: Routes = [
  {
    path: '',
    component: RegisterCaseComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_RECEPTIONIST', 'ROLE_DISPATCHER'],
      defaultSort: 'id,desc',
      pageTitle: 'arrestControlApp.registerCase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegisterCaseDetailComponent,
    resolve: {
      registerCase: RegisterCaseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_RECEPTIONIST', 'ROLE_DISPATCHER'],
      pageTitle: 'arrestControlApp.registerCase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegisterCaseUpdateComponent,
    resolve: {
      registerCase: RegisterCaseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_RECEPTIONIST', 'ROLE_DISPATCHER'],
      pageTitle: 'arrestControlApp.registerCase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegisterCaseUpdateComponent,
    resolve: {
      registerCase: RegisterCaseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_RECEPTIONIST', 'ROLE_DISPATCHER'],
      pageTitle: 'arrestControlApp.registerCase.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
