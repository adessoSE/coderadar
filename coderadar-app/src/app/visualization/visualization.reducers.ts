import * as VisualizationActions from './visualization.actions';
import {ActionReducer} from '@ngrx/store';
import {ElementAnalyzer} from '../helper/element-analyzer';
import {INode} from '../interfaces/INode';
import { IActionWithPayload } from '../interfaces/IActionWithPayload';
import { IMetric } from '../interfaces/IMetric';

export interface VisualizationState {
    availableMetricsLoading: boolean;
    availableMetrics: IMetric[];
    metricsLoading: boolean;
    metricTree: INode;
    uniqueFileList: any[];
}

const initialState: VisualizationState = {
    availableMetricsLoading: false,
    availableMetrics: [],
    metricsLoading: false,
    metricTree: null,
    uniqueFileList: []
};

export const VisualizationReducer: ActionReducer<VisualizationState> = (state = initialState, action: IActionWithPayload<any>) => {
    let newState;
    switch (action.type) {
        case VisualizationActions.LOAD_AVAILABLE_METRICS:
            newState = Object.assign({}, state);
            newState.availableMetricsLoading = true;
            newState.availableMetrics = [];
            return newState;

        case VisualizationActions.LOAD_AVAILABLE_METRICS_SUCCESS:
            newState = Object.assign({}, state);
            newState.availableMetricsLoading = false;
            newState.availableMetrics = action.payload;
            return newState;

        case VisualizationActions.LOAD_AVAILABLE_METRICS_ERROR:
            newState = Object.assign({}, state);
            newState.availableMetricsLoading = false;
            console.error(`Error while loading available metrics: ${action.payload}`);
            return state;

        case VisualizationActions.LOAD_METRIC_TREE:
            newState = Object.assign({}, state);
            newState.metricsLoading = true;
            newState.metricTree = null;
            newState.uniqueFileList = [];
            return newState;

        case VisualizationActions.LOAD_METRIC_TREE_SUCCESS:
            newState = Object.assign({}, state);
            newState.metricsLoading = false;
            newState.metricTree = action.payload;

            return newState;

        case VisualizationActions.GENERATE_UNIQUE_FILE_LIST:
            newState = Object.assign({}, state);
            newState.uniqueFileList = ElementAnalyzer.generateUniqueElementList(action.payload);

            return newState;

        case VisualizationActions.LOAD_METRIC_TREE_ERROR:
            newState = Object.assign({}, state);
            newState.metricsLoading = false;
            console.error(`Error while loading metrics: ${action.payload}`);
            return state;

        default:
            return state;
    }
};

export const getAvailableMetrics = (state: VisualizationState) => state.availableMetrics;

export const getMetricsLoading = (state: VisualizationState) => state.metricsLoading;

export const getMetricTree = (state: VisualizationState) => state.metricTree;

export const getUniqueFileList = (state: VisualizationState) => state.uniqueFileList;
