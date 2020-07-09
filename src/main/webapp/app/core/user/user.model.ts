import { IOffice } from 'app/shared/model/office.model';

export interface IUser {
  id?: any;
  login?: string;
  fullName?: string;
  telephone?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
  office?: IOffice;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public fullName?: string,
    public telephone?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string,
    public office?: IOffice
  ) {}
}
