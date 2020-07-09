import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { Arrest, ArrestType, IArrest } from 'app/shared/model/arrest.model';
import { ArrestService } from './arrest.service';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import * as moment from 'moment';
import { AccountService } from 'app/core/auth/account.service';
import { ROLE_ADMIN, ROLE_UNIT } from 'app/shared/constants/role.constants';

@Component({
  selector: 'jhi-arrest-update',
  templateUrl: './arrest-update.component.html'
})
export class ArrestUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    documentNumber: [null, [Validators.required, Validators.maxLength(255)]],
    fullName: [null, [Validators.required, Validators.maxLength(255)]],
    description: [null, [Validators.maxLength(255)]],
    type: [null, [Validators.required]],
    vehicleType: [],
    plate: [null, [Validators.maxLength(255)]],
    withDriver: [],
    arrestDate: [null, [Validators.required]]
  });

  arrest: any;

  userAdmin = false;
  userUnit = false;

  constructor(
    protected accountService: AccountService,
    protected arrestService: ArrestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.setTypeValidators();
    this.activatedRoute.data.subscribe(({ arrest }) => {
      if (!arrest.id) {
        arrest.arrestDate = moment();
      }
      this.arrest = arrest;
      this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
      this.userUnit = this.accountService.hasAnyAuthority(ROLE_UNIT);
      this.updateForm(arrest);
    });
  }

  updateForm(arrest: IArrest): void {
    this.editForm.patchValue({
      id: arrest.id,
      documentNumber: arrest.documentNumber,
      fullName: arrest.fullName,
      description: arrest.description,
      type: arrest.type,
      vehicleType: arrest.vehicleType,
      plate: arrest.plate,
      withDriver: arrest.withDriver,
      arrestDate: arrest.arrestDate ? arrest.arrestDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arrest = this.createFromForm();
    if (arrest.id !== undefined) {
      this.subscribeToSaveResponse(this.arrestService.update(arrest));
    } else {
      this.subscribeToSaveResponse(this.arrestService.create(arrest));
    }
  }

  private createFromForm(): IArrest {
    return {
      ...new Arrest(),
      id: this.editForm.get(['id'])!.value,
      documentNumber: this.editForm.get(['documentNumber'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      vehicleType: this.editForm.get(['vehicleType'])!.value,
      plate: this.editForm.get(['plate'])!.value,
      withDriver: this.editForm.get(['withDriver'])!.value,
      arrestDate:
        this.editForm.get(['arrestDate'])!.value != null ? moment(this.editForm.get(['arrestDate'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArrest>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  setTypeValidators(): void {
    const vehicleType = this.editForm.get(['vehicleType']);
    const plate = this.editForm.get(['plate']);
    const withDriver = this.editForm.get(['withDriver']);

    this.editForm.get(['type'])!.valueChanges.subscribe(type => {
      if (type === ArrestType.DRIVER) {
        withDriver!.setValue(true);
        withDriver!.setValidators([Validators.required]);
        vehicleType!.setValidators([Validators.required]);
        plate!.setValidators([Validators.required, Validators.maxLength(255)]);
      } else {
        withDriver!.setValue(null);
        vehicleType!.setValue(null);
        plate!.setValue(null);
        vehicleType!.clearValidators();
        plate!.clearValidators();
        withDriver!.clearValidators();
      }
      withDriver!.updateValueAndValidity();
      vehicleType!.updateValueAndValidity();
      plate!.updateValueAndValidity();
    });

    this.editForm.get(['withDriver'])!.valueChanges.subscribe(value => {
      if (!value) {
        if (this.editForm.get(['type'])!.value === ArrestType.DRIVER) {
          this.editForm.get(['documentNumber'])!.setValue('0');
          this.editForm.get(['fullName'])!.setValue('SIN CONDUCTOR');
        }
      }
    });
  }
}
