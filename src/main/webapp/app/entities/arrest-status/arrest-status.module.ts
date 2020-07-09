import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArrestControlSharedModule } from 'app/shared/shared.module';
import { ArrestStatusComponent } from './arrest-status.component';
import { ArrestStatusNoFineDialogComponent } from './arrest-status-no-fine-dialog.component';
import { arrestStatusRoute } from './arrest-status.route';
import { ArrestStatusPaidOutDialogComponent } from './arrest-status-paid-out-dialog.component';
import { ArrestStatusPendingDialogComponent } from 'app/entities/arrest-status/arrest-status-pending-dialog.component';

@NgModule({
  imports: [ArrestControlSharedModule, RouterModule.forChild(arrestStatusRoute)],
  declarations: [
    ArrestStatusComponent,
    ArrestStatusNoFineDialogComponent,
    ArrestStatusPaidOutDialogComponent,
    ArrestStatusPendingDialogComponent
  ],
  entryComponents: [ArrestStatusNoFineDialogComponent, ArrestStatusPaidOutDialogComponent, ArrestStatusPendingDialogComponent]
})
export class ArrestStatusModule {}
