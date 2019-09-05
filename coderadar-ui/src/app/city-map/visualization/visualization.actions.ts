import {IMetricMapping} from '../interfaces/IMetricMapping';
import {INode} from '../interfaces/INode';
import {IActionWithPayload} from '../interfaces/IActionWithPayload';
import {IMetric} from '../interfaces/IMetric';
import {Action} from '@ngrx/store';
import {Commit} from '../../model/commit';

export const LOAD_AVAILABLE_METRICS = 'LOAD_AVAILABLE_METRICS';
export const LOAD_AVAILABLE_METRICS_SUCCESS = 'LOAD_AVAILABLE_METRICS_SUCCESS';
export const LOAD_AVAILABLE_METRICS_ERROR = 'LOAD_AVAILABLE_METRICS_ERROR';
export const LOAD_METRIC_TREE = 'LOAD_METRIC_TREE';
export const LOAD_METRIC_TREE_SUCCESS = 'LOAD_METRIC_TREE_SUCCESS';
export const LOAD_METRIC_TREE_ERROR = 'LOAD_METRIC_TREE_ERROR';
export const GENERATE_UNIQUE_FILE_LIST = 'GENERATE_UNIQUE_FILE_LIST';

export function loadAvailableMetrics(): Action {
  return {
    type: LOAD_AVAILABLE_METRICS
  };
}

export function loadAvailableMetricsSuccess(metricNames: IMetric[]): IActionWithPayload<IMetric[]> {
  return {
    type: LOAD_AVAILABLE_METRICS_SUCCESS,
    payload: metricNames
  };
}

export function loadAvailableMetricsError(error: string): IActionWithPayload<string> {
  return {
    type: LOAD_AVAILABLE_METRICS_ERROR,
    payload: error
  };
}

export function loadMetricTree(
  leftCommit: Commit,
  rightCommit: Commit,
  metricMapping: IMetricMapping
): IActionWithPayload<{ leftCommit: Commit, rightCommit: Commit, metricMapping: IMetricMapping }> {
  return {
    type: LOAD_METRIC_TREE,
    payload: {
      leftCommit,
      rightCommit,
      metricMapping
    }
  };
}

export function loadMetricTreeSuccess(metricTree: INode): IActionWithPayload<INode> {
  return {
    type: LOAD_METRIC_TREE_SUCCESS,
    payload: metricTree
  };
}

export function loadMetricTreeError(error: string): IActionWithPayload<string> {
  return {
    type: LOAD_METRIC_TREE_ERROR,
    payload: error
  };
}

export function generateUniqueFileList(metricTree: INode): IActionWithPayload<INode> {
  return {
    type: GENERATE_UNIQUE_FILE_LIST,
    payload: metricTree
  };
}
