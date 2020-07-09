import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeCase } from 'app/shared/model/type-case.model';
import { TypeCaseService } from './type-case.service';

@Component({
  templateUrl: './type-case-delete-dialog.component.html'
})
export class TypeCaseDeleteDialogComponent {
  typeCase?: ITypeCase;

  constructor(protected typeCaseService: TypeCaseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeCaseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeCaseListModification');
      this.activeModal.close();
    });
  }
}
