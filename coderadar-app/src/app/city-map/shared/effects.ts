import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {from, of} from 'rxjs';
import * as actions from './actions';
import {IDeltaTreeGetErrorResponse} from '../interfaces/IDeltaTreeGetErrorResponse';
import {catchError, map, switchMap, mergeMap} from 'rxjs/operators';
import { IActionWithPayload } from '../interfaces/IActionWithPayload';
import {LOAD_AVAILABLE_METRICS, LOAD_COMMITS, LOAD_METRIC_TREE} from './actions';
import {ProjectService} from '../../service/project.service';
import {FORBIDDEN} from 'http-status-codes';
import {UserService} from '../../service/user.service';
import {HttpResponse} from '@angular/common/http';
import {INode} from '../interfaces/INode';

@Injectable()
export class AppEffects {

  public currentProjectId: number;

  @Effect()
  loadCommitsEffects$ = this.actions$.pipe(ofType(LOAD_COMMITS),
          switchMap(() => this.projectService.getCommits(this.currentProjectId)
              .then(response => {
                return actions.loadCommitsSuccess(response.body);
              }).catch(
                error => {
                  if (error.status && error.status === FORBIDDEN) { // If access is denied
                    this.userService.refresh()
                      .then(() => this.projectService.getCommits(this.currentProjectId)
                        .then(response => {
                          return actions.loadCommitsSuccess(response.body);
                        }));
                  }
                })
          )
        );

  @Effect()
  loadAvailableMetricsEffects$ = this.actions$.pipe(ofType(LOAD_AVAILABLE_METRICS),
          switchMap(() => from(this.projectService.getAvailableMetrics(this.currentProjectId))
                  .pipe(
                      mergeMap((result: any) => {
                        const availableMetrics = result.body.map(
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
      (payload) => from(this.projectService.getDeltaTree(payload.leftCommit, payload.rightCommit,
        payload.metricMapping, this.currentProjectId))
        .pipe(
            mergeMap((result: HttpResponse<INode>) => {
                return [
                    actions.loadMetricTreeSuccess(result.body),
                    actions.generateUniqueFileList(result.body)
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
        private userService: UserService
    ) { }
}
