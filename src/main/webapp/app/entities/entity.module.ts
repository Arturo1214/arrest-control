import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'type-case',
        loadChildren: () => import('./type-case/type-case.module').then(m => m.ArrestControlTypeCaseModule)
      },
      {
        path: 'register-case',
        loadChildren: () => import('./register-case/register-case.module').then(m => m.ArrestControlRegisterCaseModule)
      },
      {
        path: 'system-parameter',
        loadChildren: () => import('./system-parameter/system-parameter.module').then(m => m.SystemParameterModule)
      },
      {
        path: 'total-arrest',
        loadChildren: () => import('./total-arrest/total.arrest.module').then(m => m.TotalArrestControlArrestModule)
      },
      {
        path: 'arrest',
        loadChildren: () => import('./arrest/arrest.module').then(m => m.ArrestControlArrestModule)
      },
      {
        path: 'unit',
        loadChildren: () => import('./unit/unit.module').then(m => m.ArrestControlUnitModule)
      },
      {
        path: 'office',
        loadChildren: () => import('./office/office.module').then(m => m.ArrestControlOfficeModule)
      },
      {
        path: 'arrest-status',
        loadChildren: () => import('./arrest-status/arrest-status.module').then(m => m.ArrestStatusModule)
      },
      {
        path: 'arrest-daily',
        loadChildren: () => import('./arrest-daily/arrest-daily.module').then(m => m.ArrestDailyModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ArrestControlEntityModule {}
