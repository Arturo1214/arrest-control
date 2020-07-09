import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { AgmCoreModule } from '@agm/core';

import { ArrestControlSharedModule } from 'app/shared/shared.module';
import { RegisterCaseComponent } from './register-case.component';
import { RegisterCaseDetailComponent } from './register-case-detail.component';
import { RegisterCaseUpdateComponent } from './register-case-update.component';
import { RegisterCaseDeleteDialogComponent } from './register-case-delete-dialog.component';
import { registerCaseRoute } from './register-case.route';
import { RegisterCaseDispacthDialogComponent } from './register-case-dispatch-dialog.component';
import { RegisterCaseFinalizeDialogComponent } from './register-case-finalize-dialog.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  imports: [
    NgSelectModule,
    FontAwesomeModule,
    ArrestControlSharedModule,
    RouterModule.forChild(registerCaseRoute),
    AgmCoreModule.forRoot({
      apiKey: 'XXXXXXXXXXXXXXXXXXXXXX',
      libraries: ['places']
    })
  ],
  declarations: [
    RegisterCaseComponent,
    RegisterCaseDetailComponent,
    RegisterCaseUpdateComponent,
    RegisterCaseDeleteDialogComponent,
    RegisterCaseDispacthDialogComponent,
    RegisterCaseFinalizeDialogComponent
  ],
  entryComponents: [RegisterCaseDeleteDialogComponent, RegisterCaseDispacthDialogComponent, RegisterCaseFinalizeDialogComponent]
})
export class ArrestControlRegisterCaseModule {}
