import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-coverage-widget',
  templateUrl: './coverage-widget.component.html',
  styleUrls: ['./coverage-widget.component.css']
})
export class CoverageWidgetComponent implements OnInit {
  chartLabels = ['Coverage overall', 'Coverage on new code', 'Uncovered'];
  // @TODO use service to get metrics -this are testdata
  coverageOnNewCode = 81;
  coverageOverall = 63;
  dataCoverageOnNewCode = [0, 81, 19];
  dataCoverageOverall = [63, 0, 37];

  public chartData = [this.dataCoverageOverall, this.dataCoverageOnNewCode];
  public chartType = 'doughnut';
  public chartColors: any[] = [
    { backgroundColor: ['#016ec7', 'coral', '#d9d9d9'] },
    { backgroundColor: ['#016ec7', 'coral', '#d9d9d9'] }
  ];
  public options = {
    legend: {
      position: 'right'
    }
  };

  constructor() { }

  ngOnInit() {
  }
}
