import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {combineLatest, Observable, Subscription} from 'rxjs';
import {filter} from 'rxjs/operators';
import {Store} from '@ngrx/store';
import * as fromRoot from '../shared/reducers';
import {loadAvailableMetrics, loadMetricTree} from './visualization.actions';
import {IFilter} from '../interfaces/IFilter';
import {INode} from '../interfaces/INode';
import {IMetricMapping} from '../interfaces/IMetricMapping';
import {IMetric} from '../interfaces/IMetric';
import {ViewType} from '../enum/ViewType';
import {ComparisonPanelService} from '../service/comparison-panel.service';
import {ScreenType} from '../enum/ScreenType';
import {Commit} from '../../model/commit';
import {setMetricMapping} from '../control-panel/settings/settings.actions';
import {Project} from '../../model/project';
import {loadCommits} from '../control-panel/control-panel.actions';

@Component({
  selector: 'app-visualization',
  templateUrl: './visualization.component.html',
  styleUrls: ['./visualization.component.scss']
})
export class VisualizationComponent implements OnInit, OnDestroy, OnChanges {

  @Input() project: Project;

  metricsLoading$: Observable<boolean>;
  activeViewType$: Observable<ViewType>;
  activeFilter$: Observable<IFilter>;
  metricTree$: Observable<INode>;
  availableMetrics$: Observable<IMetric[]>;
  metricMapping$: Observable<IMetricMapping>;
  leftCommit$: Observable<Commit>;
  rightCommit$: Observable<Commit>;
  colorMetric$: Observable<IMetric>;

  subscriptions: Subscription[] = [];

  screenTypes: any = {
    left: ScreenType.LEFT,
    right: ScreenType.RIGHT
  };

  constructor(private store: Store<fromRoot.AppState>, private comparisonPanelService: ComparisonPanelService) {
  }

  ngOnInit() {
    if (this.project === undefined) {
      return;
    }

    this.metricsLoading$ = this.store.select(fromRoot.getMetricsLoading);
    this.activeViewType$ = this.store.select(fromRoot.getActiveViewType);
    this.metricTree$ = this.store.select(fromRoot.getMetricTree);
    this.availableMetrics$ = this.store.select(fromRoot.getAvailableMetrics);
    this.leftCommit$ = this.store.select(fromRoot.getLeftCommit);
    this.rightCommit$ = this.store.select(fromRoot.getRightCommit);
    this.activeFilter$ = this.store.select(fromRoot.getActiveFilter);

    const commits = this.store.select(fromRoot.getCommits);
    commits.subscribe(result => {
      if (result.length === 0) {
        this.store.dispatch(loadCommits(this.project.id));
      }
    });

    this.store.dispatch(loadAvailableMetrics(this.project.id));
    this.metricMapping$ = this.store.select(fromRoot.getMetricMapping);
    this.availableMetrics$.subscribe(metrics => {
      if (metrics.length > 0) {
        this.metricMapping$.subscribe(value => {
          if (value.heightMetricName === null) {
            const mapping: IMetricMapping = {
              heightMetricName: metrics[0].metricName,
              groundAreaMetricName: metrics[1].metricName,
              colorMetricName: metrics[2].metricName
            };
            this.store.dispatch(setMetricMapping(mapping));
          }
        });
      }
    });



    this.subscriptions.push(
      combineLatest(
        this.store.select(fromRoot.getLeftCommit),
        this.store.select(fromRoot.getRightCommit),
        this.store.select(fromRoot.getMetricMapping),
      ).pipe(
        filter(([leftCommit, rightCommit]) => !!leftCommit && !!rightCommit)
      )
        .subscribe(([leftCommit, rightCommit, metricMapping]) => {
          this.store.dispatch(loadMetricTree(leftCommit, rightCommit, metricMapping, this.project.id));
          this.comparisonPanelService.hide();
        })
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription: Subscription) => {
      subscription.unsubscribe();
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.ngOnInit();
  }

}
