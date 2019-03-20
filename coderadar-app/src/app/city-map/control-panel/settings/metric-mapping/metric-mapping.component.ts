import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {IMetricMapping} from '../../../interfaces/IMetricMapping';
import {faArrowsAlt, faArrowsAltV, faChartBar, faPalette} from '@fortawesome/free-solid-svg-icons';
import {IMetric} from '../../../interfaces/IMetric';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../../shared/reducers';

declare var $: any;

@Component({
    selector: 'app-metric-mapping',
    templateUrl: './metric-mapping.component.html',
    styleUrls: ['./metric-mapping.component.scss']
})
export class MetricMappingComponent implements OnInit {

    faChartBar = faChartBar;
    faArrowsAltV = faArrowsAltV;
    faArrowsAlt = faArrowsAlt;
    faPalette = faPalette;

    @Input() metricMapping: IMetricMapping;
    @Input() availableMetrics: IMetric[];

    @Output() metricMappingChanged = new EventEmitter();

    constructor(private store: Store<fromRoot.AppState>) {
    }

    ngOnInit() {
        // prevent bootstrap dropdown from being closed by clicking on its content
        /*$(document).on('click', '#metric-mapping-dropdown', (e) => {
            // if the button is clicked, the popup does need to be closed, so exclude the button from this exception...
            if (e.target.tagName !== 'BUTTON') {
                e.stopPropagation();
            }
        });*/
    }

    applyMetricMappings() {
        this.metricMappingChanged.emit(this.metricMapping);
    }

}
