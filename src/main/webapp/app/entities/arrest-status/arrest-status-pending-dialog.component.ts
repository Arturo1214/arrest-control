import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FormBuilder } from '@angular/forms';
import { IArrest } from 'app/shared/model/arrest.model';
import { ArrestStatusService } from './arrest-status.service';
import { ArrestService } from 'app/entities/arrest/arrest.service';
import { ArrestPending, IArrestPending } from 'app/shared/model/arrest-pending.model';

@Component({
  templateUrl: './arrest-status-pending-dialog.component.html'
})
export class ArrestStatusPendingDialogComponent {
  arrest?: IArrest;

  constructor(
    protected service: ArrestService,
    protected arrestService: ArrestStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirm(): void {
    const arrestNoFine = this.createFromForm();
    this.arrestService.updatePending(arrestNoFine).subscribe(() => {
      this.eventManager.broadcast('arrestListModification');
      this.activeModal.close();
    });
  }

  private createFromForm(): IArrestPending {
    return {
      ...new ArrestPending(),
      id: this.arrest && this.arrest.id ? this.arrest.id : 0
    };
  }
}
