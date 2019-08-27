import { NodeType } from 'src/app/city-map/enum/NodeType';
import { INode } from 'src/app/city-map/interfaces/INode';
import { IPackerElement } from 'src/app/city-map/interfaces/IPackerElement';

export enum MetricKeysEnum{
  NEW_COVERAGE = 'new_coverage',
  COVERAGE = 'coverage',
  UNCOVERED = 'uncovered'
}

export enum MetricValueType {
  INT, PERCENT
}

export interface MetricDefinition {
  id: string;
  key: string;
  type: MetricValueType;
  name: string;
}

export interface IMetricList {
  metrics: MetricDefinition[];
}

export class MetricList implements IMetricList {
  public metrics: MetricDefinition[];
}

export interface IMeasurement {
  component: { key: string, measures: IMeasure[] };
}

export interface IMeasure {
  metric: string;
  value: string;
  periods: IPeriod[];
}

export interface IPeriod {
  value: string;
  index: number;
}

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
