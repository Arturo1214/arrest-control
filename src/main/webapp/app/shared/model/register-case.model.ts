import { Moment } from 'moment';
import { StateRegister } from 'app/shared/model/enumerations/state-register.model';
import { StateCase } from 'app/shared/model/enumerations/state-case.model';
import { User } from 'app/core/user/user.model';
import { Unit } from 'app/shared/model/unit.model';
import { TypeCase } from 'app/shared/model/type-case.model';

export interface IRegisterCase {
  id?: number;
  registrationDate?: Moment;
  address?: string;
  informer?: string;
  phone?: string;
  description?: string;
  state?: StateRegister;
  zone?: string;
  acronymPatrol?: string;
  patrolLeader?: string;
  stateCase?: StateCase;
  descriptionCompletion?: string;
  latitude?: number;
  longitude?: number;
  receptionistLogin?: string;
  receptionist?: User;
  unit?: Unit;
  dispatcher?: User;
  typeCase?: TypeCase;
  checkDate?: Moment;
  supportPatrol?: string;
}

export class RegisterCase implements IRegisterCase {
  constructor(
    public id?: number,
    public registrationDate?: Moment,
    public address?: string,
    public informer?: string,
    public phone?: string,
    public description?: string,
    public state?: StateRegister,
    public zone?: string,
    public acronymPatrol?: string,
    public patrolLeader?: string,
    public stateCase?: StateCase,
    public descriptionCompletion?: string,
    public latitude?: number,
    public longitude?: number,
    public receptionistLogin?: string,
    public receptionist?: User,
    public unit?: Unit,
    public dispatcher?: User,
    public typeCase?: TypeCase,
    public checkDate?: Moment,
    public supportPatrol?: string
  ) {}
}

export interface ICreateCase {
  id?: number;
  registrationDate?: Moment;
  address?: string;
  informer?: string;
  phone?: string;
  description?: string;
  typeCaseId?: number;
  latitude?: number;
  longitude?: number;
  zone?: string;
  acronymPatrol?: string;
  patrolLeader?: string;
  supportPatrol?: string;
  unitId?: number;
}

export class CreateCase implements ICreateCase {
  constructor(
    public id?: number,
    public registrationDate?: Moment,
    public address?: string,
    public informer?: string,
    public phone?: string,
    public description?: string,
    public typeCaseId?: number,
    public latitude?: number,
    public longitude?: number,
    public zone?: string,
    public acronymPatrol?: string,
    public patrolLeader?: string,
    public supportPatrol?: string,
    public unitId?: number
  ) {}
}

export interface IDispatchCase {
  id?: number;
  zone?: string;
  acronymPatrol?: string;
  patrolLeader?: string;
  unitId?: number;
}

export class DispatchCase implements IDispatchCase {
  constructor(
    public id?: number,
    public zone?: string,
    public acronymPatrol?: string,
    public patrolLeader?: string,
    public unitId?: number
  ) {}
}

export interface IFinalizeCase {
  id?: number;
  stateCase?: StateCase;
  descriptionCompletion?: string;
}

export class FinalizeCase implements IFinalizeCase {
  constructor(public id?: number, public stateCase?: StateCase, public descriptionCompletion?: string) {}
}

export interface ICheckCase {
  id?: number;
}

export class CheckCase implements ICheckCase {
  constructor(public id?: number) {}
}
