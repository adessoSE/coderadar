export class EdgeModel {

  constructor(private startx: number, private starty: number, private endx: number, private endy: number,
              private color: string, private dashed?: boolean, private width?: number) {
  }

  equals(edge: EdgeModel): boolean {
    return this.startx === edge.startx && this.starty === edge.starty && this.endx === edge.endx &&
      this.endy === edge.endy && this.width === edge.width && this.dashed === edge.dashed;
  }
}
