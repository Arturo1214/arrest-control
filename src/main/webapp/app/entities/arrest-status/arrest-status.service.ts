import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { IArrest } from 'app/shared/model/arrest.model';
import { IArrestNoFine } from 'app/shared/model/arrest-no-fine.model';
import { IArrestPaidOut } from 'app/shared/model/arrest-paid-out.model';
import { IArrestPending } from 'app/shared/model/arrest-pending.model';

type EntityResponseType = HttpResponse<IArrest>;
type EntityArrayResponseType = HttpResponse<IArrest[]>;

@Injectable({ providedIn: 'root' })
export class ArrestStatusService {
  public resourceUrl = SERVER_API_URL + 'api/arrests';

  constructor(protected http: HttpClient) {}

  updateNoFine(arrestNoFine: IArrestNoFine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrestNoFine);
    return this.http
      .put<IArrest>(this.resourceUrl + '/no-fine', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updatePaidOut(arrestPaidOut: IArrestPaidOut): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrestPaidOut);
    return this.http
      .put<IArrest>(this.resourceUrl + '/paid-out', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updatePending(arrestPending: IArrestPending): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrestPending);
    return this.http
      .put<IArrest>(this.resourceUrl + '/pending', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
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
}
