import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArrest } from 'app/shared/model/arrest.model';
import { ArrestService } from './arrest.service';

@Component({
  templateUrl: './arrest-delete-dialog.component.html'
})
export class ArrestDeleteDialogComponent {
  arrest?: IArrest;

  constructor(protected arrestService: ArrestService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.arrestService.delete(id).subscribe(() => {
      this.eventManager.broadcast('arrestListModification');
      this.activeModal.close();
    });
  }
}
