import { NodeType } from 'src/app/city-map/enum/NodeType';
import { IPackerElement } from 'src/app/city-map/interfaces/IPackerElement';

export interface IFileNode {
  name: string;
  type: NodeType;
  metrics: any;
  renamedFrom: any;
  renamedTo: any;
  changes: any;
  children?: IFileNode[];
  packerInfo?: IPackerElement;
}


