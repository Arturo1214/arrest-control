import { TypeSystemParameter } from 'app/shared/model/enumerations/type-system-parameter.model';

export interface ISystemParameter {
  id?: number;
  description?: string;
  value?: string;
  type?: TypeSystemParameter;
}

export class SystemParameter implements ISystemParameter {
  constructor(public id?: number, public description?: string, public value?: string, public type?: TypeSystemParameter) {}
}
