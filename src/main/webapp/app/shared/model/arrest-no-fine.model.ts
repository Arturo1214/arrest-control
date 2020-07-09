export interface IArrestNoFine {
  id?: number;
  stateDescription?: string;
}

export class ArrestNoFine implements IArrestNoFine {
  constructor(public id?: number, public stateDescription?: string) {}
}
