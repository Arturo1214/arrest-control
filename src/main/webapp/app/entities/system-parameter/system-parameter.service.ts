import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISystemParameter } from 'app/shared/model/system-parameter.model';

type EntityResponseType = HttpResponse<ISystemParameter>;
type EntityArrayResponseType = HttpResponse<ISystemParameter[]>;

@Injectable({ providedIn: 'root' })
export class SystemParameterService {
  public resourceUrl = SERVER_API_URL + 'api/system-parameters';

  constructor(protected http: HttpClient) {}

  update(systemParameter: ISystemParameter): Observable<EntityResponseType> {
    return this.http.put<ISystemParameter>(this.resourceUrl, systemParameter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISystemParameter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISystemParameter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
