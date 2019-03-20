import {IMetric} from './IMetric';

export interface IAvailableMetricsGetResponse {
    _embedded: {
        metricResourceList: IMetric[]
    };
}
