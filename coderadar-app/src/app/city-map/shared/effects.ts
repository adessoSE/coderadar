import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {from, of} from 'rxjs';
import * as actions from './actions';
import {LOAD_AVAILABLE_METRICS, LOAD_COMMITS, LOAD_METRIC_TREE, loadAvailableMetrics, loadCommits, loadMetricTree} from './actions';
import {IDeltaTreeGetErrorResponse} from '../interfaces/IDeltaTreeGetErrorResponse';
import {catchError, map, mergeMap, switchMap} from 'rxjs/operators';
import {IActionWithPayload} from '../interfaces/IActionWithPayload';
import {ProjectService} from '../../service/project.service';
import {UserService} from '../../service/user.service';
import {HttpResponse} from '@angular/common/http';
import {INode} from '../interfaces/INode';
import {ICommitsGetErrorResponse} from '../interfaces/ICommitsGetErrorResponse';
import {Store} from '@ngrx/store';
import * as fromRoot from './reducers';

@Injectable()
export class AppEffects {

  @Effect()
  loadCommitsEffects$ = this.actions$.pipe(ofType(LOAD_COMMITS),
    map((action: IActionWithPayload<number>) => action.payload),
    switchMap(
      (payload) => from(this.projectService.getCommits(payload))
        .pipe(
          map((result: any) => {
            return actions.loadCommitsSuccess(result.body);
          }),
          catchError((response: ICommitsGetErrorResponse) => {
            this.userService.refresh().then(() => this.store.dispatch(loadCommits(payload)));
            return of(actions.loadCommitsError(response.error));
          })
        )
    )
  );

  @Effect()
  loadAvailableMetricsEffects$ = this.actions$.pipe(ofType(LOAD_AVAILABLE_METRICS),
    map((action: IActionWithPayload<number>) => action.payload),
    switchMap((payload) => from(this.projectService.getAvailableMetrics(payload))
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
          return [actions.loadAvailableMetricsSuccess(availableMetrics)];
        }),
        catchError((response: any) => {
          this.userService.refresh().then(() => this.store.dispatch(loadAvailableMetrics(payload)));
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
        payload.metricMapping, payload.projectId))
        .pipe(
          mergeMap((result: HttpResponse<INode>) => {
            return [
              actions.loadMetricTreeSuccess(result.body),
              actions.generateUniqueFileList(result.body)
            ];
          }),
          catchError((response: IDeltaTreeGetErrorResponse) => {
            this.userService.refresh().then(() => this.store.dispatch(loadMetricTree(payload.leftCommit, payload.rightCommit,
              payload.metricMapping,  payload.projectId)));
            return of(actions.loadMetricTreeError(response.error));
          })
        )
    )
  );

  constructor(
    private store: Store<fromRoot.AppState>,
    private actions$: Actions<IActionWithPayload<any>>,
    private projectService: ProjectService,
    private userService: UserService
  ) {}
}
