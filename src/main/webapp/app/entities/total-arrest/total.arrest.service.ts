import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITotalArrest } from 'app/shared/model/total.arrest.model';

type EntityArrayResponseType = HttpResponse<ITotalArrest[]>;

@Injectable({ providedIn: 'root' })
export class TotalArrestService {
  public resourceUrl = SERVER_API_URL + 'api/arrests';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITotalArrest[]>(this.resourceUrl + '/total', { params: options, observe: 'response' });
  }
}
