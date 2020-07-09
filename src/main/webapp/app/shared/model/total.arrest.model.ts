import { IUser } from 'app/core/user/user.model';
import { Moment } from 'moment';
import { IUnit } from 'app/shared/model/unit.model';
import { IOffice } from 'app/shared/model/office.model';

export interface ITotalArrest {
  unit?: IUnit;
  totalArrested?: number;
  totalPedestrian?: number;
  totalPassenger?: number;
  totalDriver?: number;
  totalMotorized?: number;
  totalVehicle?: number;
  totalMotorcycle?: number;
  detailTotals?: IDetailTotal[];
}

export interface IDetailTotal {
  office?: IOffice;
  totalArrested?: number;
  totalPedestrian?: number;
  totalPassenger?: number;
  totalDriver?: number;
  totalMotorized?: number;
  totalVehicle?: number;
  totalMotorcycle?: number;
}

export class TotalArrest implements ITotalArrest {
  constructor(
    public unit?: IUnit,
    public totalArrested?: number,
    public totalPedestrian?: number,
    public totalPassenger?: number,
    public totalDriver?: number,
    public totalMotorized?: number,
    public totalVehicle?: number,
    public totalMotorcycle?: number,
    public detailTotals?: IDetailTotal[]
  ) {}
}

export class DetailTotal implements IDetailTotal {
  constructor(
    public office?: IOffice,
    public totalArrested?: number,
    public totalPedestrian?: number,
    public totalPassenger?: number,
    public totalDriver?: number,
    public totalMotorized?: number,
    public totalVehicle?: number,
    public totalMotorcycle?: number
  ) {}
}
