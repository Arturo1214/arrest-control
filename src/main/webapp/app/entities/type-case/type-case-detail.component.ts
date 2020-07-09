import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeCase } from 'app/shared/model/type-case.model';

@Component({
  selector: 'jhi-type-case-detail',
  templateUrl: './type-case-detail.component.html'
})
export class TypeCaseDetailComponent implements OnInit {
  typeCase: ITypeCase | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCase }) => (this.typeCase = typeCase));
  }

  previousState(): void {
    window.history.back();
  }
}
