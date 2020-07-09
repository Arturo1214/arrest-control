import { Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { TotalArrestComponent } from './total.arrest.component';

export const totalArrestRoute: Routes = [
  {
    path: '',
    component: TotalArrestComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_REPORT', 'ROLE_UNIT'],
      defaultSort: 'id,desc',
      pageTitle: 'arrestControlApp.arrest.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
