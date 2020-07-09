import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegisterCase } from 'app/shared/model/register-case.model';
import { StateRegister } from 'app/shared/model/enumerations/state-register.model';

@Component({
  selector: 'jhi-register-case-detail',
  templateUrl: './register-case-detail.component.html'
})
export class RegisterCaseDetailComponent implements OnInit {
  registerCase: IRegisterCase | null = null;
  notAssigned = StateRegister.NOT_ASSIGNED;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ registerCase }) => (this.registerCase = registerCase));
  }

  previousState(): void {
    window.history.back();
  }
}
