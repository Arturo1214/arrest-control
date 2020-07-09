import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArrest } from 'app/shared/model/arrest.model';

type EntityResponseType = HttpResponse<IArrest>;
type EntityArrayResponseType = HttpResponse<IArrest[]>;

@Injectable({ providedIn: 'root' })
export class ArrestService {
  public resourceUrl = SERVER_API_URL + 'api/arrests';

  constructor(protected http: HttpClient) {}

  create(arrest: IArrest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrest);
    return this.http
      .post<IArrest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(arrest: IArrest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrest);
    return this.http
      .put<IArrest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArrest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArrest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(arrest: IArrest): IArrest {
    const copy: IArrest = Object.assign({}, arrest, {
      arrestDate: arrest.arrestDate && arrest.arrestDate.isValid() ? arrest.arrestDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.arrestDate = res.body.arrestDate ? moment(res.body.arrestDate) : undefined;
    }
    return res;
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
