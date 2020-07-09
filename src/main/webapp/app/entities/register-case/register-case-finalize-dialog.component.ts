import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FormBuilder, Validators } from '@angular/forms';
import { FinalizeCase, IFinalizeCase, IRegisterCase } from 'app/shared/model/register-case.model';
import { RegisterCaseService } from './register-case.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { HttpResponse } from '@angular/common/http';
import { IUser } from 'app/core/user/user.model';

type SelectableEntity = IUser | IUnit;

@Component({
  templateUrl: './register-case-finalize-dialog.component.html'
})
export class RegisterCaseFinalizeDialogComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];
  registerCase?: IRegisterCase;

  editForm = this.fb.group({
    id: [],
    stateCase: [null, [Validators.required, Validators.required]],
    descriptionCompletion: [null, [Validators.required, Validators.maxLength(1024)]]
  });

  constructor(
    protected registerCaseService: RegisterCaseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    protected unitService: UnitService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    if (this.registerCase) {
      this.updateForm(this.registerCase);
    }
    this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
  }

  updateForm(registerCase: IRegisterCase): void {
    this.editForm.patchValue({
      id: registerCase.id,
      stateCase: registerCase.stateCase,
      descriptionCompletion: registerCase.descriptionCompletion
    });
  }

  private createFromForm(): IFinalizeCase {
    return {
      ...new FinalizeCase(),
      id: this.editForm.get(['id'])!.value,
      stateCase: this.editForm.get(['stateCase'])!.value,
      descriptionCompletion: this.editForm.get(['descriptionCompletion'])!.value
    };
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmFinalize(): void {
    this.isSaving = true;
    const registerCase = this.createFromForm();
    this.registerCaseService.finalize(registerCase).subscribe(() => {
      this.isSaving = false;
      this.eventManager.broadcast('registerCaseListModification');
      this.activeModal.close();
    });
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
