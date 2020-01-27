import { Metric } from './../interfaces/metricValuesTreeResponse';
import { Component, OnInit, Input } from '@angular/core';
import { MetricValuesTreeResponse } from '../interfaces/metricValuesTreeResponse';

// ViewModel
export interface HeadMonopolyResultItem {
  filename: string;
  metricValue: number;
  percentageValue: number;
  tooltipText: string;
}

@Component({
  selector: 'app-head-monopoly-widget',
  templateUrl: './head-monopoly-widget.component.html',
  styleUrls: ['./head-monopoly-widget.component.css']
})
export class HeadMonopolyWidgetComponent implements OnInit {
  @Input()
  metricValuesTreeResponse: MetricValuesTreeResponse;
  @Input()
  titel: string;
  @Input()
  subTitel: string;

  headMonopoly: HeadMonopolyResultItem[] = [];
  dialog: any;
  configDialog: any;

  constructor() { }

  ngOnInit() {
    // sort the metricValuesTreeResponse by the product of it's metrics
    this.metricValuesTreeResponse.children.sort((a, b) => this.calculateHeadMonopoly(b.metrics) - this.calculateHeadMonopoly(a.metrics));
    // the first metric value is the highest value. It will be used to calculate the percentage value.
    const maxMetricValue = this.calculateHeadMonopoly(this.metricValuesTreeResponse.children[0].metrics);

    // Map the metricValuesTreeResponse to the ViewModel
    this.metricValuesTreeResponse.children.forEach(element => {
      const metricProduct = this.calculateHeadMonopoly(element.metrics);
      const metricPercentage = (metricProduct / maxMetricValue) * 100;
      const toolTipText = 'Knowledge Bottleneck: ' + metricProduct + '\n' + this.toolTipTextMetric(element.metrics);
      this.headMonopoly.push({
        filename: element.name,
        metricValue: metricProduct,
        percentageValue: metricPercentage,
        tooltipText: toolTipText
      });
    });
  }

  /**
   * Build string with metric values
   * @param metrics : Metric[]
   */
  private toolTipTextMetric(metrics: Metric[]) {
    let result = '';
    metrics.forEach(element => {
      result += element.metricName + ': ' + element.value;
    });
    return result;
  }

  /**
   * Calculate the product of metrics
   * @param metrics : Metric[]
   */
  private calculateHeadMonopoly(metrics: Metric[]) {
    let result = 1;
    metrics.forEach(element => {
      result *= element.value;
    });
    return result;
  }

}
