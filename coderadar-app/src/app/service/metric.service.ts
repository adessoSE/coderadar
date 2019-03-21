import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IDeltaTreeGetResponse} from '../city-map/interfaces/IDeltaTreeGetResponse';
import {ICommit} from '../city-map/interfaces/ICommit';
import {INode} from '../city-map/interfaces/INode';
import {IMetricMapping} from '../city-map/interfaces/IMetricMapping';
import {AppConfig} from '../AppConfig';
import {map} from 'rxjs/operators';
import {IAvailableMetricsGetResponse} from '../city-map/interfaces/IAvailableMetricsGetResponse';

@Injectable()
export class MetricService {

    constructor(private http: HttpClient) {
    }

    loadAvailableMetrics(projectId: number): Observable<IAvailableMetricsGetResponse> {
      return this.http.get<IAvailableMetricsGetResponse>(`${AppConfig.BASE_URL}/projects/${projectId}/metrics`);
    }

    loadDeltaTree(leftCommit: ICommit, rightCommit: ICommit, metricMapping: IMetricMapping, projectId: number):
      Observable<IDeltaTreeGetResponse> {
      const body = {
          commit1: leftCommit.name,
          commit2: rightCommit.name,
          metrics: [metricMapping.heightMetricName, metricMapping.groundAreaMetricName, metricMapping.colorMetricName]
      };

      return this.http.post<INode>(`${AppConfig.BASE_URL}/projects/${projectId}/metricvalues/deltaTree`, body)
          .pipe(
              map((res) => {
                  return {
                      rootNode: res
                  };
              })
          );
    }
}
