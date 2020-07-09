import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-user-mgmt-delete-dialog',
  templateUrl: './user-management-reset-password-dialog.component.html'
})
export class UserManagementResetPasswordDialogComponent {
  user?: User;

  constructor(private userService: UserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmResetPassword(id: number): void {
    this.userService.resetPassword(id).subscribe(() => {
      this.eventManager.broadcast('userListModification');
      this.activeModal.close();
    });
  }
}
