export interface IPackerElement {
  fit?: {
    x: number,
    y: number,
    w: number,
    h: number,
    used: boolean,
    right: {
      x: number,
      y: number,
      w: number,
      h: number
    },
    down: {
      x: number,
      y: number,
      w: number,
      h: number
    }
  };
  w: number;
  h: number;
  renderedX?: number;
  renderedY?: number;
}
