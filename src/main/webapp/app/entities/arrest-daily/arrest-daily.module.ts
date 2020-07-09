import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArrestControlSharedModule } from 'app/shared/shared.module';
import { ArrestDailyComponent } from './arrest-daily.component';
import { arrestDailyRoute } from './arrest-daily.route';

@NgModule({
  imports: [ArrestControlSharedModule, RouterModule.forChild(arrestDailyRoute)],
  declarations: [ArrestDailyComponent],
  entryComponents: []
})
export class ArrestDailyModule {}
