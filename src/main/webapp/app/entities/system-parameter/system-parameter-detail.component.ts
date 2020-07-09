import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemParameter } from 'app/shared/model/system-parameter.model';

@Component({
  selector: 'jhi-system-parameter-detail',
  templateUrl: './system-parameter-detail.component.html'
})
export class SystemParameterDetailComponent implements OnInit {
  systemParameter: ISystemParameter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ systemParameter }) => (this.systemParameter = systemParameter));
  }

  previousState(): void {
    window.history.back();
  }
}
