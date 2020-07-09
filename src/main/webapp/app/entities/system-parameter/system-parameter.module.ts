import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArrestControlSharedModule } from 'app/shared/shared.module';
import { SystemParameterComponent } from './system-parameter.component';
import { SystemParameterDetailComponent } from './system-parameter-detail.component';
import { systemParameterRoute } from './system-parameter.route';
import { SystemParameterUpdateComponent } from 'app/entities/system-parameter/system-parameter-update.component';

@NgModule({
  imports: [ArrestControlSharedModule, RouterModule.forChild(systemParameterRoute)],
  declarations: [SystemParameterComponent, SystemParameterUpdateComponent, SystemParameterDetailComponent]
})
export class SystemParameterModule {}
