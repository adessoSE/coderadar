import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../shared/reducers';
import {changeActiveFilter, changeViewType, setMetricMapping} from './settings.actions';
import {IFilter} from '../../interfaces/IFilter';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {IMetric} from '../../interfaces/IMetric';
import {Observable} from 'rxjs';
import {ViewType} from '../../../model/enum/ViewType';

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

    activeViewType$: Observable<ViewType>;
    activeFilter$: Observable<IFilter>;
    availableMetrics$: Observable<IMetric[]>;
    metricMapping$: Observable<IMetricMapping>;

    constructor(private store: Store<fromRoot.AppState>) {
    }

    ngOnInit() {
        this.activeViewType$ = this.store.select(fromRoot.getActiveViewType);
        this.activeFilter$ = this.store.select(fromRoot.getActiveFilter);
        this.availableMetrics$ = this.store.select(fromRoot.getAvailableMetrics);
        this.metricMapping$ = this.store.select(fromRoot.getMetricMapping);
    }

    handleViewTypeChanged(viewType: ViewType) {
        this.store.dispatch(changeViewType(viewType));
    }

    handleFilterChanged(filter: IFilter) {
        this.store.dispatch(changeActiveFilter(filter));
    }

    handleMetricMappingChanged(metricMapping: IMetricMapping) {
        this.store.dispatch(setMetricMapping(metricMapping));
    }

}
