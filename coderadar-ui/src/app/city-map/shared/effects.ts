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
import {Project} from '../../model/project';

@Injectable()
export class AppEffects {

  public currentProject: Project;
  isLoaded = false;

  @Effect()
  loadCommitsEffects$ = this.actions$.pipe(ofType(LOAD_COMMITS),
    switchMap(
      () => from(this.projectService.getCommits(this.currentProject.id, this.currentProject.defaultBranch))
        .pipe(
          map((result: any) => {
            return actions.loadCommitsSuccess(result.body);
          }),
          catchError((response: ICommitsGetErrorResponse) => {
            this.userService.refresh(() => this.store.dispatch(loadCommits()));
            return of(actions.loadCommitsError(response.error));
          })
        )
    )
  );

  @Effect()
  loadAvailableMetricsEffects$ = this.actions$.pipe(ofType(LOAD_AVAILABLE_METRICS),
    switchMap(() => from(this.projectService.getAvailableMetrics(this.currentProject.id))
      .pipe(
        mergeMap((result: any) => {
          const availableMetrics = result.body.map(
            metric => {
              const shortName = metric.split('.').pop();
              return {
                metricName: metric,
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
          this.userService.refresh(() => this.store.dispatch(loadAvailableMetrics()));
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
        payload.metricMapping, this.currentProject.id))
        .pipe(
          mergeMap((result: HttpResponse<INode>) => {
            return [
              actions.loadMetricTreeSuccess(result.body),
              actions.generateUniqueFileList(result.body)
            ];
          }),
          catchError((response: IDeltaTreeGetErrorResponse) => {
            this.userService.refresh(() => this.store.dispatch(loadMetricTree(payload.leftCommit, payload.rightCommit,
              payload.metricMapping)));
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
