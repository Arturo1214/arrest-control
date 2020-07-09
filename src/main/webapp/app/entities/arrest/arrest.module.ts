import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArrestControlSharedModule } from 'app/shared/shared.module';
import { ArrestComponent } from './arrest.component';
import { ArrestDetailComponent } from './arrest-detail.component';
import { ArrestUpdateComponent } from './arrest-update.component';
import { ArrestDeleteDialogComponent } from './arrest-delete-dialog.component';
import { arrestRoute } from './arrest.route';

@NgModule({
  imports: [ArrestControlSharedModule, RouterModule.forChild(arrestRoute)],
  declarations: [ArrestComponent, ArrestDetailComponent, ArrestUpdateComponent, ArrestDeleteDialogComponent],
  entryComponents: [ArrestDeleteDialogComponent]
})
export class ArrestControlArrestModule {}
