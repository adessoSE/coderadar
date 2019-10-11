import {Geometry, Line, LineBasicMaterial, QuadraticBezierCurve3, Vector3} from 'three';
import {NodeType} from '../enum/NodeType';
import {VisualizationConfig} from '../VisualizationConfig';

export class BlockConnection {

  curveObject: Line;

  constructor(fromElement, toElement) {
    const from = fromElement.position.clone();
    from.x += fromElement.scale.x / 2;
    from.y += fromElement.scale.y;
    from.z += fromElement.scale.z / 2;

    const to = toElement.position.clone();
    to.x += toElement.scale.x / 2;
    to.y += toElement.scale.y;
    to.z += toElement.scale.z / 2;

    const distance = from.distanceTo(to);

    const via = new Vector3((from.x + to.x) / 2, this.getHeightByDistance(distance), (from.z + to.z) / 2);

    const curve = new QuadraticBezierCurve3(from, via, to);

    const geometry = new Geometry();
    geometry.vertices = curve.getPoints(50);
    const material = new LineBasicMaterial({color: VisualizationConfig.COLOR_CONNECTION});
    this.curveObject = new Line(geometry, material);

    this.curveObject.userData = {
      type: NodeType.CONNECTION,
      changeTypes: {
        renamed: true
      }
    };
  }

  getCurve(): Line {
    return this.curveObject;
  }

  private getHeightByDistance(distance) {
    return 0.0001 * Math.pow(distance, 2) + 0.8 * distance + 30;
  }
}
