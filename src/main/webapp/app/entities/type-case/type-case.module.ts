import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArrestControlSharedModule } from 'app/shared/shared.module';
import { TypeCaseComponent } from './type-case.component';
import { TypeCaseDetailComponent } from './type-case-detail.component';
import { TypeCaseUpdateComponent } from './type-case-update.component';
import { TypeCaseDeleteDialogComponent } from './type-case-delete-dialog.component';
import { typeCaseRoute } from './type-case.route';

@NgModule({
  imports: [ArrestControlSharedModule, RouterModule.forChild(typeCaseRoute)],
  declarations: [TypeCaseComponent, TypeCaseDetailComponent, TypeCaseUpdateComponent, TypeCaseDeleteDialogComponent],
  entryComponents: [TypeCaseDeleteDialogComponent]
})
export class ArrestControlTypeCaseModule {}
