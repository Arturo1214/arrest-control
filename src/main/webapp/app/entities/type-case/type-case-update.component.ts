import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeCase, TypeCase } from 'app/shared/model/type-case.model';
import { TypeCaseService } from './type-case.service';

@Component({
  selector: 'jhi-type-case-update',
  templateUrl: './type-case-update.component.html'
})
export class TypeCaseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]]
  });

  constructor(protected typeCaseService: TypeCaseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCase }) => {
      this.updateForm(typeCase);
    });
  }

  updateForm(typeCase: ITypeCase): void {
    this.editForm.patchValue({
      id: typeCase.id,
      name: typeCase.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeCase = this.createFromForm();
    if (typeCase.id !== undefined) {
      this.subscribeToSaveResponse(this.typeCaseService.update(typeCase));
    } else {
      this.subscribeToSaveResponse(this.typeCaseService.create(typeCase));
    }
  }

  private createFromForm(): ITypeCase {
    return {
      ...new TypeCase(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCase>>): void {
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
}
