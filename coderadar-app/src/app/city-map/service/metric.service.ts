import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IDeltaTreeGetResponse} from '../interfaces/IDeltaTreeGetResponse';
import {ICommit} from '../interfaces/ICommit';
import {INode} from '../interfaces/INode';
import {IMetricMapping} from '../interfaces/IMetricMapping';
import {map} from 'rxjs/operators';
import {IAvailableMetricsGetResponse} from '../interfaces/IAvailableMetricsGetResponse';
import {AppComponent} from '../../app.component';

@Injectable()
export class MetricService {

    constructor(private http: HttpClient) {
    }

    loadAvailableMetrics(projectId: number): Observable<IAvailableMetricsGetResponse> {
      return this.http.get<IAvailableMetricsGetResponse>(`${AppComponent.getApiUrl()}projects/${projectId}/metrics`);
    }

    loadDeltaTree(leftCommit: ICommit, rightCommit: ICommit, metricMapping: IMetricMapping, projectId: number):
      Observable<IDeltaTreeGetResponse> {
      const body = {
          commit1: leftCommit.name,
          commit2: rightCommit.name,
          metrics: [metricMapping.heightMetricName, metricMapping.groundAreaMetricName, metricMapping.colorMetricName]
      };

      return this.http.post<INode>(`${AppComponent.getApiUrl()}projects/${projectId}/metricvalues/deltaTree`, body)
          .pipe(
              map((res) => {
                  return {
                      rootNode: res
                  };
              })
          );
    }
}
