import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICheckCase, IDispatchCase, IFinalizeCase, IRegisterCase } from 'app/shared/model/register-case.model';

type EntityResponseType = HttpResponse<IRegisterCase>;
type EntityArrayResponseType = HttpResponse<IRegisterCase[]>;

@Injectable({ providedIn: 'root' })
export class RegisterCaseService {
  public resourceUrl = SERVER_API_URL + 'api/register-cases';

  constructor(protected http: HttpClient) {}

  create(registerCase: IRegisterCase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registerCase);
    return this.http
      .post<IRegisterCase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(registerCase: IRegisterCase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registerCase);
    return this.http
      .put<IRegisterCase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  dispatch(registerCase: IDispatchCase): Observable<EntityResponseType> {
    return this.http
      .put<IRegisterCase>(this.resourceUrl + '/dispatch', registerCase, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  check(check: ICheckCase): Observable<EntityResponseType> {
    return this.http
      .put<IRegisterCase>(this.resourceUrl + '/check', check, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  finalize(registerCase: IFinalizeCase): Observable<EntityResponseType> {
    return this.http
      .put<IRegisterCase>(this.resourceUrl + '/finalize', registerCase, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRegisterCase>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegisterCase[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryCount(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl + '/count', { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(registerCase: IRegisterCase): IRegisterCase {
    const copy: IRegisterCase = Object.assign({}, registerCase, {
      registrationDate:
        registerCase.registrationDate && registerCase.registrationDate.isValid() ? registerCase.registrationDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registrationDate = res.body.registrationDate ? moment(res.body.registrationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((registerCase: IRegisterCase) => {
        registerCase.registrationDate = registerCase.registrationDate ? moment(registerCase.registrationDate) : undefined;
      });
    }
    return res;
  }
}
