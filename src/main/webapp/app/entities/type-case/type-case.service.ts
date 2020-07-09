import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeCase } from 'app/shared/model/type-case.model';

type EntityResponseType = HttpResponse<ITypeCase>;
type EntityArrayResponseType = HttpResponse<ITypeCase[]>;

@Injectable({ providedIn: 'root' })
export class TypeCaseService {
  public resourceUrl = SERVER_API_URL + 'api/type-cases';

  constructor(protected http: HttpClient) {}

  create(typeCase: ITypeCase): Observable<EntityResponseType> {
    return this.http.post<ITypeCase>(this.resourceUrl, typeCase, { observe: 'response' });
  }

  update(typeCase: ITypeCase): Observable<EntityResponseType> {
    return this.http.put<ITypeCase>(this.resourceUrl, typeCase, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeCase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeCase[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeCase[]>(this.resourceUrl + '/all', { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
