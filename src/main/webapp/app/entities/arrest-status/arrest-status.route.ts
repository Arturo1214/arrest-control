import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArrest, Arrest } from 'app/shared/model/arrest.model';
import { ArrestStatusComponent } from './arrest-status.component';
import { ArrestService } from 'app/entities/arrest/arrest.service';

@Injectable({ providedIn: 'root' })
export class ArrestStatusRoute implements Resolve<IArrest> {
  constructor(private arrestService: ArrestService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArrest> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.arrestService.find(id).pipe(
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

export const arrestStatusRoute: Routes = [
  {
    path: '',
    component: ArrestStatusComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_UNIT'],
      defaultSort: 'id,desc',
      pageTitle: 'arrestControlApp.arrest.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
