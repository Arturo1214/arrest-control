import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArrestControlSharedModule } from 'app/shared/shared.module';
import { UserManagementComponent } from './user-management.component';
import { UserManagementDetailComponent } from './user-management-detail.component';
import { UserManagementUpdateComponent } from './user-management-update.component';
import { UserManagementDeleteDialogComponent } from './user-management-delete-dialog.component';
import { userManagementRoute } from './user-management.route';
import { UserManagementResetPasswordDialogComponent } from 'app/admin/user-management/user-management-reset-password-dialog.component';

@NgModule({
  imports: [ArrestControlSharedModule, RouterModule.forChild(userManagementRoute)],
  declarations: [
    UserManagementComponent,
    UserManagementDetailComponent,
    UserManagementUpdateComponent,
    UserManagementDeleteDialogComponent,
    UserManagementResetPasswordDialogComponent
  ],
  entryComponents: [UserManagementDeleteDialogComponent, UserManagementResetPasswordDialogComponent]
})
export class UserManagementModule {}
