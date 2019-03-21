import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import * as actions from './actions';
import {CommitService} from '../../service/commit.service';
import {ICommitsGetResponse} from '../interfaces/ICommitsGetResponse';
import {ICommitsGetErrorResponse} from '../interfaces/ICommitsGetErrorResponse';
import {IDeltaTreeGetErrorResponse} from '../interfaces/IDeltaTreeGetErrorResponse';
import {IDeltaTreeGetResponse} from '../interfaces/IDeltaTreeGetResponse';
import {MetricService} from '../../service/metric.service';
import {catchError, map, switchMap, mergeMap} from 'rxjs/operators';
import { IActionWithPayload } from '../interfaces/IActionWithPayload';
import { IAvailableMetricsGetResponse } from '../interfaces/IAvailableMetricsGetResponse';
import { IAvailableMetricsGetErrorResponse } from '../interfaces/IAvailableMetricsGetErrorResponse';
import { IMetric } from '../interfaces/IMetric';
import { AppConfig } from '../../AppConfig';
import {LOAD_AVAILABLE_METRICS, LOAD_COMMITS, LOAD_METRIC_TREE} from './actions';
import {INode} from '../interfaces/INode';
import {ICommit} from '../interfaces/ICommit';
import {IMetricMapping} from '../interfaces/IMetricMapping';

@Injectable()
export class AppEffects {

    @Effect()
    loadCommitsEffects$ = this.actions$.pipe(ofType(LOAD_COMMITS),
            switchMap(
                () => this.commitService.loadCommits()
                    .pipe(
                        map((result: any) => {
                          console.log(result);
                          return actions.loadCommitsSuccess(result);
                        }),
                        catchError((response: ICommitsGetErrorResponse) => {
                            return of(actions.loadCommitsError(response.error));
                        })
                    )
            )
        );

    @Effect()
    loadAvailableMetricsEffects$ = this.actions$.pipe(ofType(LOAD_AVAILABLE_METRICS),
            switchMap(
                () => this.metricService.loadAvailableMetrics()
                    .pipe(
                        mergeMap((result: any) => {
                          const availableMetrics = result.map(
                                metric => {
                                  const shortName = metric.metricName.split('.').pop();
                                  return {
                                    metricName: metric.metricName,
                                    shortName
                                  };
                                }
                            );
                            // TODO: Error handling when less than three metrics are available
                          return [
                                actions.loadAvailableMetricsSuccess(availableMetrics),
                                actions.setMetricMapping({
                                    heightMetricName: availableMetrics[0].metricName,
                                    groundAreaMetricName: availableMetrics[1].metricName,
                                    colorMetricName: availableMetrics[2].metricName
                                })
                            ];
                        }),
                        catchError((response: any) => {
                          return of(actions.loadAvailableMetricsError(response.error));
                        })
                    )
            )
        );


  @Effect()
  loadMetricTreeEffects$ = this.actions$.pipe(ofType(LOAD_METRIC_TREE),
    map((action: IActionWithPayload<any>) => action.payload),
    switchMap(
      (payload) => this.metricService.loadDeltaTree(payload.leftCommit, payload.rightCommit, payload.metricMapping)
        .pipe(
            mergeMap((result: IDeltaTreeGetResponse) => {
                return [
                    actions.loadMetricTreeSuccess(result.rootNode),
                    actions.generateUniqueFileList(result.rootNode)
                ];
            }),
            catchError((response: IDeltaTreeGetErrorResponse) => {
                console.log(response);
                return of(actions.loadMetricTreeError(response.error));
            })
        )
    )
  );

    constructor(
        private actions$: Actions<IActionWithPayload<any>>,
        private commitService: CommitService,
        private metricService: MetricService
    ) { }
}
