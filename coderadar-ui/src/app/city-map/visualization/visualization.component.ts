import {Component, OnDestroy, OnInit} from '@angular/core';
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

@Component({
  selector: 'app-visualization',
  templateUrl: './visualization.component.html',
  styleUrls: ['./visualization.component.scss']
})
export class VisualizationComponent implements OnInit, OnDestroy {

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
    this.metricsLoading$ = this.store.select(fromRoot.getMetricsLoading);
    this.activeViewType$ = this.store.select(fromRoot.getActiveViewType);
    this.activeFilter$ = this.store.select(fromRoot.getActiveFilter);
    this.metricTree$ = this.store.select(fromRoot.getMetricTree);
    this.availableMetrics$ = this.store.select(fromRoot.getAvailableMetrics);
    this.metricMapping$ = this.store.select(fromRoot.getMetricMapping);
    this.leftCommit$ = this.store.select(fromRoot.getLeftCommit);
    this.rightCommit$ = this.store.select(fromRoot.getRightCommit);

    this.subscriptions.push(
      combineLatest(
        this.store.select(fromRoot.getLeftCommit),
        this.store.select(fromRoot.getRightCommit),
        this.store.select(fromRoot.getMetricMapping)
      ).pipe(
        filter(([leftCommit, rightCommit, metricMapping]) => !!leftCommit && !!rightCommit)
      )
        .subscribe(([leftCommit, rightCommit, metricMapping]) => {
          this.store.dispatch(loadMetricTree(leftCommit, rightCommit, metricMapping));
          this.comparisonPanelService.hide();
        })
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription: Subscription) => {
      subscription.unsubscribe();
    });
  }

}
