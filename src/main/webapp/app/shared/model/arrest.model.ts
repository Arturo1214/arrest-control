import { IUser } from 'app/core/user/user.model';
import { Moment } from 'moment';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { IOffice } from 'app/shared/model/office.model';

export const enum ArrestType {
  PEDESTRIAN = 'PEDESTRIAN',
  DRIVER = 'DRIVER',
  PASSENGER = 'PASSENGER'
}

export const enum VehicleType {
  VEHICLE = 'VEHICLE',
  MOTORCYCLE = 'MOTORCYCLE'
}

export interface IArrest {
  id?: number;
  documentNumber?: string;
  fullName?: string;
  description?: string;
  type?: ArrestType;
  vehicleType?: VehicleType;
  plate?: string;
  arrestDate?: Moment;
  status?: PaymentStatus;
  stateDescription?: string;
  depositNumber?: string;
  withDriver?: boolean;
  office?: IOffice;
  user?: IUser;
  userStatus?: IUser;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export class Arrest implements IArrest {
  constructor(
    public id?: number,
    public documentNumber?: string,
    public fullName?: string,
    public description?: string,
    public type?: ArrestType,
    public vehicleType?: VehicleType,
    public plate?: string,
    public arrestDate?: Moment,
    public status?: PaymentStatus,
    public stateDescription?: string,
    public depositNumber?: string,
    public withDriver?: boolean,
    public office?: IOffice,
    public user?: IUser,
    public userStatus?: IUser,
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date
  ) {}
}
