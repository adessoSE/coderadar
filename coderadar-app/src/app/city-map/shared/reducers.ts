import {createSelector} from 'reselect';
import * as fromControlPanel from '../control-panel/control-panel.reducers';
import * as fromSettings from '../control-panel/settings/settings.reducers';
import * as fromVisualization from '../visualization/visualization.reducers';
import {InjectionToken} from '@angular/core';
import {ActionReducerMap} from '@ngrx/store';

export interface AppState {
    controlPanelState: fromControlPanel.ControlPanelState;
    settingsState: fromSettings.SettingsState;
    visualizationState: fromVisualization.VisualizationState;
}

export const REDUCER_TOKEN = new InjectionToken<ActionReducerMap<AppState>>('Registered Reducers');

export function getReducers() {
    return {
        controlPanelState: fromControlPanel.ControlPanelReducer,
        settingsState: fromSettings.SettingsReducer,
        visualizationState: fromVisualization.VisualizationReducer
    };
}

export const getControlPanelState = (state: AppState) => state.controlPanelState;

export const getVisualizationState = (state: AppState) => state.visualizationState;

export const getSettingsState = (state: AppState) => state.settingsState;

export const getCommitsLoading = createSelector(getControlPanelState, fromControlPanel.getCommitsLoading);

export const getCommits = createSelector(getControlPanelState, fromControlPanel.getCommits);

export const getLeftCommit = createSelector(getControlPanelState, fromControlPanel.getLeftCommit);

export const getRightCommit = createSelector(getControlPanelState, fromControlPanel.getRightCommit);

export const getMetricsLoading = createSelector(getVisualizationState, fromVisualization.getMetricsLoading);

export const getMetricTree = createSelector(getVisualizationState, fromVisualization.getMetricTree);

export const getUniqueFileList = createSelector(getVisualizationState, fromVisualization.getUniqueFileList);

export const getAvailableMetrics = createSelector(getVisualizationState, fromVisualization.getAvailableMetrics);

export const getMetricMapping = createSelector(getSettingsState, fromSettings.getMetricMapping);

export const getActiveFilter = createSelector(getSettingsState, fromSettings.getActiveFilter);

export const getActiveViewType = createSelector(getSettingsState, fromSettings.getActiveViewType);
