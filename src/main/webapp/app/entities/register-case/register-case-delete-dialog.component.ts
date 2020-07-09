import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegisterCase } from 'app/shared/model/register-case.model';
import { RegisterCaseService } from './register-case.service';

@Component({
  templateUrl: './register-case-delete-dialog.component.html'
})
export class RegisterCaseDeleteDialogComponent {
  registerCase?: IRegisterCase;

  constructor(
    protected registerCaseService: RegisterCaseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.registerCaseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('registerCaseListModification');
      this.activeModal.close();
    });
  }
}
