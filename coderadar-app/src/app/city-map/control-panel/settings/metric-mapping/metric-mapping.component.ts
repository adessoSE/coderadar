import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {IMetricMapping} from '../../../interfaces/IMetricMapping';
import {faArrowsAlt, faArrowsAltV, faChartBar, faPalette} from '@fortawesome/free-solid-svg-icons';
import {IMetric} from '../../../interfaces/IMetric';

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

  constructor() {
  }

  ngOnInit() {
  }

  applyMetricMappings() {
    this.metricMappingChanged.emit(this.metricMapping);
  }

}
