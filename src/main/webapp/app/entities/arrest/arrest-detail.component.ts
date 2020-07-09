import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArrest } from 'app/shared/model/arrest.model';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { ROLE_ADMIN, ROLE_UNIT } from 'app/shared/constants/role.constants';
import { IUnit } from 'app/shared/model/unit.model';

@Component({
  selector: 'jhi-arrest-detail',
  templateUrl: './arrest-detail.component.html'
})
export class ArrestDetailComponent implements OnInit {
  arrest: IArrest | null = null;

  currentAccount: Account | null = null;
  userAdmin = false;
  userUnit = false;

  constructor(protected accountService: AccountService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.userAdmin = this.accountService.hasAnyAuthority(ROLE_ADMIN);
    this.userUnit = this.accountService.hasAnyAuthority(ROLE_UNIT);
    this.accountService.identity().subscribe(value => (this.currentAccount = value));
    this.activatedRoute.data.subscribe(({ arrest }) => (this.arrest = arrest));
  }

  previousState(): void {
    window.history.back();
  }

  viewEdit(unit: IUnit): boolean {
    if (!this.userAdmin && !this.userUnit) {
      return false;
    } else {
      if (this.userAdmin) {
        return true;
      } else {
        if (this.currentAccount) {
          if (this.currentAccount.office && this.currentAccount.office.unit) {
            if (this.currentAccount.office.unit.id === unit.id) {
              return true;
            }
          }
        }
        return false;
      }
    }
  }
}
