import { Moment } from 'moment';

export interface IArrestDaily {
  userLogin?: string;
  arrestDate?: Moment;
  unitId?: number;
  officeId?: number;
}

export class ArrestDaily implements IArrestDaily {
  constructor(public userLogin?: string, public arrestDate?: Moment, public unitId?: number, public officeId?: number) {}
}
