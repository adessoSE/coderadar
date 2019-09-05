import {IPackerElement} from './IPackerElement';
import {NodeType} from '../enum/NodeType';

export interface INode {
  name: string;
  type: NodeType;
  commit1Metrics: any;
  commit2Metrics: any;
  renamedFrom: any;
  renamedTo: any;
  changes: any;
  children?: INode[];
  packerInfo?: IPackerElement;
}
