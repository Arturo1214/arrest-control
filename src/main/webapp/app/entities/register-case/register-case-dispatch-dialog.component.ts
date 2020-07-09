import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FormBuilder, Validators } from '@angular/forms';
import { DispatchCase, IDispatchCase, IRegisterCase } from 'app/shared/model/register-case.model';
import { RegisterCaseService } from './register-case.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { HttpResponse } from '@angular/common/http';
import { IUser } from 'app/core/user/user.model';

type SelectableEntity = IUser | IUnit;

@Component({
  templateUrl: './register-case-dispatch-dialog.component.html'
})
export class RegisterCaseDispacthDialogComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];
  registerCase?: IRegisterCase;

  editForm = this.fb.group({
    id: [],
    zone: [null, [Validators.required, Validators.maxLength(255)]],
    acronymPatrol: [null, [Validators.required, Validators.maxLength(255)]],
    patrolLeader: [null, [Validators.required, Validators.maxLength(255)]],
    unitId: [null, Validators.required]
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
      zone: registerCase.zone,
      acronymPatrol: registerCase.acronymPatrol,
      patrolLeader: registerCase.patrolLeader,
      unitId: registerCase.unit ? registerCase.unit.id : 0
    });
  }

  private createFromForm(): IDispatchCase {
    return {
      ...new DispatchCase(),
      id: this.editForm.get(['id'])!.value,
      zone: this.editForm.get(['zone'])!.value,
      acronymPatrol: this.editForm.get(['acronymPatrol'])!.value,
      patrolLeader: this.editForm.get(['patrolLeader'])!.value,
      unitId: this.editForm.get(['unitId'])!.value
    };
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDispatch(): void {
    this.isSaving = true;
    const registerCase = this.createFromForm();
    this.registerCaseService.dispatch(registerCase).subscribe(() => {
      this.isSaving = false;
      this.eventManager.broadcast('registerCaseListModification');
      this.activeModal.close();
    });
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
