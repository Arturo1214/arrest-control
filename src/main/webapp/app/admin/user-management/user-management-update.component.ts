import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LANGUAGES } from 'app/core/language/language.constants';
import { User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IUnit } from 'app/shared/model/unit.model';
import { IOffice } from 'app/shared/model/office.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { HttpResponse } from '@angular/common/http';
import { OfficeService } from 'app/entities/office/office.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { ROLE_ADMIN, ROLE_UNIT, ROLE_USER } from 'app/shared/constants/role.constants';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html'
})
export class UserManagementUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  user!: User;
  languages = LANGUAGES;
  authorities: string[] = [];
  isSaving = false;
  units?: IUnit[];
  offices?: IOffice[];
  userAdmin = false;
  userUnit = false;

  editForm = this.fb.group({
    id: [],
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    fullName: ['', [Validators.maxLength(250)]],
    telephone: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [],
    langKey: [],
    authorities: [Validators.required],
    unit: [Validators.required],
    office: [Validators.required]
  });

  constructor(
    protected accountService: AccountService,
    protected officeService: OfficeService,
    protected unitService: UnitService,
    private userService: UserService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.user = user;
        if (this.user.id === undefined) {
          this.user.activated = true;
        }
        this.updateForm(user);
      }

      this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
      this.userUnit = this.accountService.hasAnyAuthority(ROLE_UNIT);

      if (this.userAdmin) {
        this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
        this.userService.authorities().subscribe(authorities => {
          this.authorities = authorities;
        });
      } else {
        this.accountService.getAuthenticationState().subscribe(account => {
          this.currentAccount = account;
          this.units = account && account.office && account.office.unit ? [account.office.unit] : [];
          if (this.user.id === undefined) {
            this.authorities = [ROLE_USER];
          } else {
            this.authorities = [ROLE_USER];
            if (this.currentAccount) {
              if (this.currentAccount.login === this.user.login) {
                this.authorities = [ROLE_UNIT];
              }
            }
          }
        });
      }
    });

    this.onChanges();
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== undefined) {
      this.userService.update(this.user).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    } else {
      this.userService.create(this.user).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    }
  }

  private updateForm(user: User): void {
    if (user.office) {
      if (user.office.unit) {
        this.officeService
          .query({
            'unitId.equals': user.office.unit.id
          })
          .subscribe((res: HttpResponse<IUnit[]>) => (this.offices = res.body || []));
      }
    }

    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      fullName: user.fullName,
      telephone: user.telephone,
      email: user.email,
      activated: user.activated,
      langKey: user.langKey,
      authorities: user.authorities,
      unit: user.office ? user.office.unit : null,
      office: user.office
    });
  }

  private updateUser(user: User): void {
    user.login = this.editForm.get(['login'])!.value;
    user.fullName = this.editForm.get(['fullName'])!.value;
    user.telephone = this.editForm.get(['telephone'])!.value;
    user.email = this.editForm.get(['email'])!.value;
    user.activated = this.editForm.get(['activated'])!.value;
    user.langKey = this.editForm.get(['langKey'])!.value;
    user.authorities = this.editForm.get(['authorities'])!.value;
    user.office = this.editForm.get(['office'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  trackUnitById(index: number, item: IUnit): any {
    return item.id;
  }

  trackById(index: number, item: IOffice): any {
    return item.id;
  }

  onChanges(): void {
    this.editForm.get(['unit'])!.valueChanges.subscribe(value => {
      const unit: IUnit = value;
      if (unit) {
        this.officeService
          .query({
            'unitId.equals': unit.id
          })
          .subscribe((res: HttpResponse<IUnit[]>) => (this.offices = res.body || []));

        this.editForm.patchValue({
          office: null
        });
      }
    });
  }
}
