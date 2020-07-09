export interface ITypeCase {
  id?: number;
  name?: string;
}

export class TypeCase implements ITypeCase {
  constructor(public id?: number, public name?: string) {}
}
