import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import * as actions from './actions';
import {CommitService} from '../../service/commit.service';
import {ICommitsGetErrorResponse} from '../interfaces/ICommitsGetErrorResponse';
import {IDeltaTreeGetErrorResponse} from '../interfaces/IDeltaTreeGetErrorResponse';
import {IDeltaTreeGetResponse} from '../interfaces/IDeltaTreeGetResponse';
import {MetricService} from '../../service/metric.service';
import {catchError, map, switchMap, mergeMap} from 'rxjs/operators';
import { IActionWithPayload } from '../interfaces/IActionWithPayload';
import {LOAD_AVAILABLE_METRICS, LOAD_COMMITS, LOAD_METRIC_TREE} from './actions';
import {ProjectService} from '../../service/project.service';
import {FORBIDDEN} from 'http-status-codes';
import {UserService} from '../../service/user.service';

@Injectable()
export class AppEffects {

    public currentProjectId: number;

    @Effect()
    loadCommitsEffects$ = this.actions$.pipe(ofType(LOAD_COMMITS),
            switchMap(() => this.projectService.getCommits(this.currentProjectId)
                .then(response => {
                  return actions.loadCommitsSuccess(response.body);
                })
            )
          );

    @Effect()
    loadAvailableMetricsEffects$ = this.actions$.pipe(ofType(LOAD_AVAILABLE_METRICS),
            switchMap(() => this.metricService.loadAvailableMetrics(this.currentProjectId)
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
      (payload) => this.metricService.loadDeltaTree(payload.leftCommit, payload.rightCommit, payload.metricMapping, this.currentProjectId)
        .pipe(
            mergeMap((result: IDeltaTreeGetResponse) => {
                return [
                    actions.loadMetricTreeSuccess(result.rootNode),
                    actions.generateUniqueFileList(result.rootNode)
                ];
            }),
            catchError((response: IDeltaTreeGetErrorResponse) => {
                return of(actions.loadMetricTreeError(response.error));
            })
        )
    )
  );

    constructor(
        private actions$: Actions<IActionWithPayload<any>>,
        private projectService: ProjectService,
        private metricService: MetricService,
        private userService: UserService
    ) { }
}
