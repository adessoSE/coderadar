import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IDeltaTreeGetResponse} from '../interfaces/IDeltaTreeGetResponse';
import {ICommit} from '../interfaces/ICommit';
import {INode} from '../interfaces/INode';
import {IMetricMapping} from '../interfaces/IMetricMapping';
import {AppConfig} from '../AppConfig';
import {delay, map} from 'rxjs/operators';
import { IAvailableMetricsGetResponse } from '../interfaces/IAvailableMetricsGetResponse';
import {environment} from '../../environments/environment';

@Injectable()
export class MetricService {

    constructor(private http: HttpClient) {
    }

    loadAvailableMetrics(): Observable<IAvailableMetricsGetResponse> {
      return this.http.get<IAvailableMetricsGetResponse>(`${AppConfig.BASE_URL}/projects/18/metrics`);
    }

    loadDeltaTree(leftCommit: ICommit, rightCommit: ICommit, metricMapping: IMetricMapping): Observable<IDeltaTreeGetResponse> {
      const body = {
          commit1: leftCommit.name,
          commit2: rightCommit.name,
          metrics: [metricMapping.heightMetricName, metricMapping.groundAreaMetricName, metricMapping.colorMetricName]
      };

      return this.http.post<INode>(`${AppConfig.BASE_URL}/projects/18/metricvalues/deltaTree`, body)
          .pipe(
              map((res) => {
                  return {
                      rootNode: res
                  };
              })
          );
    }
}
