import { IOffice } from 'app/shared/model/office.model';

export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public fullName: string,
    public telephone: string,
    public langKey: string,
    public login: string,
    public imageUrl: string,
    public office: IOffice
  ) {}
}
