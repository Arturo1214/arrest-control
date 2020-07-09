export interface IArrestPending {
  id?: number;
}

export class ArrestPending implements IArrestPending {
  constructor(public id?: number) {}
}
