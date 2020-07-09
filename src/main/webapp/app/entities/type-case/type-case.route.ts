import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeCase, TypeCase } from 'app/shared/model/type-case.model';
import { TypeCaseService } from './type-case.service';
import { TypeCaseComponent } from './type-case.component';
import { TypeCaseDetailComponent } from './type-case-detail.component';
import { TypeCaseUpdateComponent } from './type-case-update.component';

@Injectable({ providedIn: 'root' })
export class TypeCaseResolve implements Resolve<ITypeCase> {
  constructor(private service: TypeCaseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeCase> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeCase: HttpResponse<TypeCase>) => {
          if (typeCase.body) {
            return of(typeCase.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeCase());
  }
}

export const typeCaseRoute: Routes = [
  {
    path: '',
    component: TypeCaseComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'arrestControlApp.typeCase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeCaseDetailComponent,
    resolve: {
      typeCase: TypeCaseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'arrestControlApp.typeCase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeCaseUpdateComponent,
    resolve: {
      typeCase: TypeCaseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'arrestControlApp.typeCase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeCaseUpdateComponent,
    resolve: {
      typeCase: TypeCaseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'arrestControlApp.typeCase.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
