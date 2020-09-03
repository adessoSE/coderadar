import {IPackerElement} from './IPackerElement';
import {NodeType} from '../enum/NodeType';
import {MetricValue} from '../../model/metric-value';

export interface INode {
  name: string;
  type: NodeType;
  commit1Metrics: MetricValue[];
  commit2Metrics: MetricValue[];
  renamedFrom: any;
  renamedTo: any;
  changes: any;
  children?: INode[];
  packerInfo?: IPackerElement;
}
