import * as SettingsActions from './settings.actions';
import {ActionReducer} from '@ngrx/store';
import {IFilter} from '../../interfaces/IFilter';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {IActionWithPayload} from '../../interfaces/IActionWithPayload';
import {ViewType} from '../../enum/ViewType';

export interface SettingsState {
  activeViewType: ViewType;
  activeFilter: IFilter;
  metricMapping: IMetricMapping;
}

const initialState: SettingsState = {
  activeViewType: ViewType.SPLIT,
  activeFilter: {
    unmodified: true,
    modified: true,
    deleted: true,
    added: true,
    renamed: true
  },
  metricMapping: {
    heightMetricName: null,
    groundAreaMetricName: null,
    colorMetricName: null
  }
};

export const SettingsReducer: ActionReducer<SettingsState> = (state = initialState, action: IActionWithPayload<any>) => {
  let newState;
  switch (action.type) {
    case SettingsActions.CHANGE_VIEW_TYPE:
      newState = Object.assign({}, state);
      newState.activeViewType = action.payload;
      return newState;

    case SettingsActions.CHANGE_ACTIVE_FILTER:
      newState = Object.assign({}, state);
      newState.activeFilter = Object.assign({}, state.activeFilter, action.payload);
      return newState;

    case SettingsActions.SET_METRIC_MAPPING:
      newState = Object.assign({}, state);
      newState.metricMapping = Object.assign({}, state.metricMapping, action.payload);
      return newState;

    default:
      return state;
  }
};

export const getActiveViewType = (state: SettingsState) => state.activeViewType;

export const getActiveFilter = (state: SettingsState) => state.activeFilter;

export const getMetricMapping = (state: SettingsState) => state.metricMapping;
