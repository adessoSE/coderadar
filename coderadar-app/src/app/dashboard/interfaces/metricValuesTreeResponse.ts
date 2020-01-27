/**
 * Interface from 'Querying Metrics for all Files in a Single Commit'
 * @see metricvalues/tree service
 */
export interface MetricValuesTreeResponse {
    name: string;
    type: string;
    metrics: Metric [];
    children: MetricValuesTreeResponse [];
}

export interface Metric {
    metricName: string;
    value: number;
}
