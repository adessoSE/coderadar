export class Module {
  public id: number;
  public modulePath: string;

  constructor(id: number, modulePath: string) {
    this.id = id;
    this.modulePath = modulePath;
  }
}
