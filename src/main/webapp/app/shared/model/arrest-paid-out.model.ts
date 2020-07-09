export interface IArrestPaidOut {
  id?: number;
  stateDescription?: string;
  depositNumber?: string;
}

export class ArrestPaidOut implements IArrestPaidOut {
  constructor(public id?: number, public stateDescription?: string, public depositNumber?: string) {}
}
