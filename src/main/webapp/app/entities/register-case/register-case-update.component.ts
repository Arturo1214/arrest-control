import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { CreateCase, ICreateCase, IRegisterCase } from 'app/shared/model/register-case.model';
import { RegisterCaseService } from './register-case.service';
import { IUser } from 'app/core/user/user.model';
import { IUnit } from 'app/shared/model/unit.model';
import { ITypeCase } from 'app/shared/model/type-case.model';
import { TypeCaseService } from 'app/entities/type-case/type-case.service';
import { AccountService } from 'app/core/auth/account.service';
import { ROLE_ADMIN, ROLE_DISPATCHER } from 'app/shared/constants/role.constants';
import { UnitService } from 'app/entities/unit/unit.service';

type SelectableEntity = IUser | IUnit | ITypeCase;

@Component({
  selector: 'jhi-register-case-update',
  templateUrl: './register-case-update.component.html',
  styleUrls: ['./register-case-update.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterCaseUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];
  typecases: ITypeCase[] = [];

  latitude = -17.7892458;
  longitude = -63.1708306;
  zoom = 15;

  userAdmin = false;
  userDispatcher = false;

  editForm = this.fb.group({
    id: [],
    registrationDate: [null, [Validators.required]],
    address: [null, [Validators.required, Validators.maxLength(255)]],
    informer: [null, [Validators.required, Validators.maxLength(255)]],
    phone: [null, [Validators.required, Validators.maxLength(255)]],
    description: [null, [Validators.required, Validators.maxLength(1024)]],
    typeCaseId: [null, Validators.required],
    latitude: [null, [Validators.max(9999999999.999999)]],
    longitude: [null, [Validators.max(9999999999.999999)]],

    zone: [null, []],
    acronymPatrol: [null, []],
    patrolLeader: [null, []],
    supportPatrol: [null, []],
    unitId: [null, Validators.required]
  });

  constructor(
    protected unitService: UnitService,
    protected accountService: AccountService,
    protected typeCaseService: TypeCaseService,
    protected registerCaseService: RegisterCaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
    this.userDispatcher = this.accountService.hasAnyAuthority(ROLE_DISPATCHER);

    this.activatedRoute.data.subscribe(({ registerCase }) => {
      if (!registerCase.id) {
        const today = moment();
        registerCase.registrationDate = today;
      }

      this.changeCoords();
      this.updateForm(registerCase);
      this.setCurrentLocation(registerCase);
      this.typeCaseService.queryAll().subscribe((res: HttpResponse<ITypeCase[]>) => (this.typecases = res.body || []));
      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
      this.setTypeValidators();
    });
  }

  private setTypeValidators(): void {
    const zone = this.editForm.get(['zone']);
    const acronymPatrol = this.editForm.get(['acronymPatrol']);
    const patrolLeader = this.editForm.get(['patrolLeader']);
    const unitId = this.editForm.get(['unitId']);

    if (this.userDispatcher) {
      zone!.setValidators([Validators.required, Validators.maxLength(255)]);
      acronymPatrol!.setValidators([Validators.required, Validators.maxLength(255)]);
      patrolLeader!.setValidators([Validators.required, Validators.maxLength(255)]);
      unitId!.setValidators([Validators.required]);
    } else {
      zone!.clearValidators();
      acronymPatrol!.clearValidators();
      patrolLeader!.clearValidators();
      unitId!.clearValidators();
    }
    zone!.updateValueAndValidity();
    acronymPatrol!.updateValueAndValidity();
    patrolLeader!.updateValueAndValidity();
    unitId!.updateValueAndValidity();
  }

  private setCurrentLocation(registerCase: IRegisterCase): void {
    if (registerCase.latitude && registerCase.longitude) {
      this.latitude = registerCase.latitude;
      this.longitude = registerCase.longitude;
      this.zoom = 15;
    } else {
      if ('geolocation' in navigator) {
        navigator.geolocation.getCurrentPosition(position => {
          this.latitude = position.coords.latitude;
          this.longitude = position.coords.longitude;
          this.zoom = 15;
        });
      }
    }
  }

  updateForm(registerCase: IRegisterCase): void {
    this.editForm.patchValue({
      id: registerCase.id,
      registrationDate: registerCase.registrationDate ? registerCase.registrationDate.format(DATE_TIME_FORMAT) : null,
      address: registerCase.address,
      typeCaseId: registerCase.typeCase ? registerCase.typeCase.id : undefined,
      informer: registerCase.informer,
      phone: registerCase.phone,
      description: registerCase.description,
      latitude: registerCase.latitude ? registerCase.latitude : this.latitude,
      longitude: registerCase.longitude ? registerCase.longitude : this.longitude,
      zone: registerCase.zone,
      acronymPatrol: registerCase.acronymPatrol,
      patrolLeader: registerCase.patrolLeader,
      supportPatrol: registerCase.supportPatrol,
      unitId: registerCase.unit ? registerCase.unit.id : 0
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const registerCase = this.createFromForm();
    if (registerCase.id !== undefined) {
      this.subscribeToSaveResponse(this.registerCaseService.update(registerCase));
    } else {
      this.subscribeToSaveResponse(this.registerCaseService.create(registerCase));
    }
  }

  private createFromForm(): ICreateCase {
    return {
      ...new CreateCase(),
      id: this.editForm.get(['id'])!.value,
      registrationDate: this.editForm.get(['registrationDate'])!.value
        ? moment(this.editForm.get(['registrationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      address: this.editForm.get(['address'])!.value,
      typeCaseId: this.editForm.get(['typeCaseId'])!.value,
      informer: this.editForm.get(['informer'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      description: this.editForm.get(['description'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      zone: this.editForm.get(['zone'])!.value,
      acronymPatrol: this.editForm.get(['acronymPatrol'])!.value,
      patrolLeader: this.editForm.get(['patrolLeader'])!.value,
      supportPatrol: this.editForm.get(['supportPatrol'])!.value,
      unitId: this.editForm.get(['unitId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegisterCase>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  markerDragEnd($event: MouseEvent): void {
    // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
    // @ts-ignore
    this.latitude = $event.coords.lat;
    // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
    // @ts-ignore
    this.longitude = $event.coords.lng;

    this.editForm.patchValue({
      latitude: this.latitude,
      longitude: this.longitude
    });
  }

  changeCoords(): void {
    this.editForm.get(['latitude'])!.valueChanges.subscribe(value => {
      this.latitude = value;
    });

    this.editForm.get(['longitude'])!.valueChanges.subscribe(value => {
      this.longitude = value;
    });
  }
}
