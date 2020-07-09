import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOffice, IOfficeDTO, OfficeDTO } from 'app/shared/model/office.model';
import { OfficeService } from './office.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';

@Component({
  selector: 'jhi-office-update',
  templateUrl: './office-update.component.html'
})
export class OfficeUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    unit: [null, Validators.required]
  });

  constructor(
    protected officeService: OfficeService,
    protected unitService: UnitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ office }) => {
      this.updateForm(office);

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
    });
  }

  updateForm(office: IOffice): void {
    this.editForm.patchValue({
      id: office.id,
      name: office.name,
      unit: office.unit
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const office = this.createFromForm();
    if (office.id !== undefined) {
      this.subscribeToSaveResponse(this.officeService.update(office));
    } else {
      this.subscribeToSaveResponse(this.officeService.create(office));
    }
  }

  private createFromForm(): IOfficeDTO {
    return {
      ...new OfficeDTO(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      unitId: this.editForm.get(['unit'])!.value.id
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffice>>): void {
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

  trackById(index: number, item: IUnit): any {
    return item.id;
  }
}
