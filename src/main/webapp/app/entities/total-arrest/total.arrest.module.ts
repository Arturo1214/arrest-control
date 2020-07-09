import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArrestControlSharedModule } from 'app/shared/shared.module';
import { TotalArrestComponent } from './total.arrest.component';
import { totalArrestRoute } from './total.arrest.route';

@NgModule({
  imports: [ArrestControlSharedModule, RouterModule.forChild(totalArrestRoute)],
  declarations: [TotalArrestComponent],
  entryComponents: []
})
export class TotalArrestControlArrestModule {}
