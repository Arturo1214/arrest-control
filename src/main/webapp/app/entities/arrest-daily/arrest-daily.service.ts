import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { IArrest } from 'app/shared/model/arrest.model';
import { createRequestOption } from 'app/shared/util/request-util';

type EntityResponseType = HttpResponse<IArrest>;
type EntityArrayResponseType = HttpResponse<IArrest[]>;

@Injectable({ providedIn: 'root' })
export class ArrestDailyService {
  public resourceUrl = SERVER_API_URL + 'api/arrests/list';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArrest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((arrest: IArrest) => {
        arrest.arrestDate = arrest.arrestDate ? moment(arrest.arrestDate) : undefined;
      });
    }
    return res;
  }
}
