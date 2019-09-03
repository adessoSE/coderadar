import {IFilter} from '../../interfaces/IFilter';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {IActionWithPayload} from '../../interfaces/IActionWithPayload';
import {ViewType} from '../../enum/ViewType';

export const CHANGE_VIEW_TYPE = 'CHANGE_VIEW_TYPE';
export const CHANGE_ACTIVE_FILTER = 'CHANGE_ACTIVE_FILTER';
export const SET_METRIC_MAPPING = 'SET_METRIC_MAPPING';

export function changeViewType(viewType: ViewType): IActionWithPayload<ViewType> {
  return {
    type: CHANGE_VIEW_TYPE,
    payload: viewType
  };
}

export function changeActiveFilter(filter: IFilter): IActionWithPayload<IFilter> {
  return {
    type: CHANGE_ACTIVE_FILTER,
    payload: filter
  };
}

export function setMetricMapping(mapping: IMetricMapping): IActionWithPayload<IMetricMapping> {
  return {
    type: SET_METRIC_MAPPING,
    payload: mapping
  };
}
