import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-coverage-widget',
  templateUrl: './coverage-widget.component.html',
  styleUrls: ['./coverage-widget.component.css']
})
export class CoverageWidgetComponent implements OnInit {
  chartLabels = ['Coverage on new code', 'Coverage overall', 'Uncovered'];
  dataCoverageOnNewCode = [81, 0, 19];
  dataCoverageOverall = [0, 63, 37];

  public chartData = [this.dataCoverageOnNewCode, this.dataCoverageOverall];
  public chartType = 'doughnut';
  public chartColors: any[] = [
    { backgroundColor: ['#008000', '#8080ff', '#ff4d4d'] },
    { backgroundColor: ['#008000', '#8080ff', '#ff4d4d'] }
  ];
  public options = {
    legend: {
      position: 'bottom'
    }
  };

  constructor() { }

  ngOnInit() {
  }
}
