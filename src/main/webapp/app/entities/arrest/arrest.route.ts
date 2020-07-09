import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArrest, Arrest } from 'app/shared/model/arrest.model';
import { ArrestService } from './arrest.service';
import { ArrestComponent } from './arrest.component';
import { ArrestDetailComponent } from './arrest-detail.component';
import { ArrestUpdateComponent } from './arrest-update.component';

@Injectable({ providedIn: 'root' })
export class ArrestResolve implements Resolve<IArrest> {
  constructor(private service: ArrestService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArrest> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((arrest: HttpResponse<Arrest>) => {
          if (arrest.body) {
            return of(arrest.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Arrest());
  }
}

export const arrestRoute: Routes = [
  {
    path: '',
    component: ArrestComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_UNIT', 'ROLE_USER', 'ROLE_REPORT'],
      defaultSort: 'id,desc',
      pageTitle: 'arrestControlApp.arrest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArrestDetailComponent,
    resolve: {
      arrest: ArrestResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_UNIT', 'ROLE_USER', 'ROLE_REPORT'],
      pageTitle: 'arrestControlApp.arrest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArrestUpdateComponent,
    resolve: {
      arrest: ArrestResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER'],
      pageTitle: 'arrestControlApp.arrest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArrestUpdateComponent,
    resolve: {
      arrest: ArrestResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_UNIT'],
      pageTitle: 'arrestControlApp.arrest.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
