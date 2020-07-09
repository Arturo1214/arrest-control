import { IUnit, Unit } from 'app/shared/model/unit.model';

export interface IOffice {
  id?: number;
  name?: string;
  unit?: IUnit;
}

export class Office implements IOffice {
  constructor(public id?: number, public name?: string, public unit?: Unit) {}
}

export interface IOfficeDTO {
  id?: number;
  name?: string;
  unitId?: number;
}

export class OfficeDTO implements IOfficeDTO {
  constructor(public id?: number, public name?: string, public unitId?: number) {}
}
