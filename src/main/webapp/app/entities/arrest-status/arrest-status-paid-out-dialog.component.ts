import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FormBuilder, Validators } from '@angular/forms';
import { Arrest, IArrest } from 'app/shared/model/arrest.model';
import { ArrestStatusService } from './arrest-status.service';
import { ArrestService } from 'app/entities/arrest/arrest.service';
import { ArrestPaidOut, IArrestPaidOut } from 'app/shared/model/arrest-paid-out.model';

@Component({
  templateUrl: './arrest-status-paid-out-dialog.component.html'
})
export class ArrestStatusPaidOutDialogComponent implements OnInit {
  arrest?: IArrest;

  editForm = this.fb.group({
    id: [],
    stateDescription: [null, [Validators.maxLength(255)]],
    depositNumber: [null, [Validators.required, Validators.maxLength(255)]]
  });

  constructor(
    protected service: ArrestService,
    protected arrestService: ArrestStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.updateForm(this.arrest ? this.arrest : new Arrest());
  }

  updateForm(arrest: IArrest): void {
    this.editForm.patchValue({
      id: arrest.id,
      stateDescription: arrest.stateDescription,
      depositNumber: arrest.depositNumber
    });
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirm(): void {
    const arrestNoFine = this.createFromForm();
    this.arrestService.updatePaidOut(arrestNoFine).subscribe(() => {
      this.eventManager.broadcast('arrestListModification');
      this.activeModal.close();
    });
  }

  private createFromForm(): IArrestPaidOut {
    return {
      ...new ArrestPaidOut(),
      id: this.editForm.get(['id'])!.value,
      depositNumber: this.editForm.get(['depositNumber'])!.value,
      stateDescription: this.editForm.get(['stateDescription'])!.value
    };
  }
}
