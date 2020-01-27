import { HttpfetcherService } from './../services/httpfetcher.service';
import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatRadioChange, MatPaginator } from '@angular/material';
import { DataPoint } from '../interfaces/history';
import { IssueSevertyEnum, IssueTypeEnum, FetchedIssues } from '../interfaces/issue';

export enum GraphType {
  OVERALL = 'Overall', LEAKPERIOD = 'Leak Period'
}
@Component({
  selector: 'app-issue-widget',
  templateUrl: './issue-widget.component.html',
  styleUrls: ['./issue-widget.component.scss']
})
export class IssueWidgetComponent implements OnInit, OnDestroy {
  @ViewChild(MatSort, {}) sort: MatSort;
  @ViewChild(MatPaginator, {}) paginator: MatPaginator;

  public barChartType = 'horizontalBar';
  public graphs: string[] = [GraphType.OVERALL, GraphType.LEAKPERIOD];
  public barOptions = {
    maintainAspectRatio: false,
    responsive : false
  };

  // Tab Severity
  public chartSeverityLabels = [IssueSevertyEnum.BLOCKER, IssueSevertyEnum.CRITICAL,
                              IssueSevertyEnum.MAJOR, IssueSevertyEnum.MINOR, IssueSevertyEnum.INFO];
  public chartSeverityData = [];
  public chartSeverityDataLeakPeriod = [];
  public chartSeverityDataOverall = [];
  public selectedSeverityGraph = GraphType.LEAKPERIOD;

  // Tab Type
  public chartIssueTypeLabels = [IssueTypeEnum.BUG, IssueTypeEnum.VULNERABILITY, IssueTypeEnum.CODE_SMELL];
  public chartIssueTypeData = [];
  public chartIssueTypeDataLeakPeriod = [];
  public chartIssueTypeDataOverall = [];
  public selectedIssueTypeGraph = GraphType.LEAKPERIOD;

  // Tab Trend
  chartTrendLabels: string [] = [];
  chartTrendData: number [] = [];
  chartTrendDatasets = [ ];

  historyMetricObserver = {
    next: (resp: any) => {
      resp.metricValues.forEach(metric => {
        metric.points.forEach((element: DataPoint) => {
          let label = '';
          element.x.forEach(x => {
            label += x;
          });
          // Check label is already in list
          if (!this.chartTrendLabels.includes(label)) {
            this.chartTrendLabels.push(label);
          }
          this.chartTrendData.push(element.y);
        });
      });
    },
    error: (err: any) => console.log(err)
  };

  /**
   * Get testdata from service
   */
  private getHistory() {
    this.service.getIssueBugHistory().subscribe(this.historyMetricObserver);
  }


  // Tab Issues
  public displayedIssueColumns = [
    'severity',
    'type',
    'component',
    'creationDate',
    'message'
  ];
  totalIssues: number;
  leakPeriodIssues: number;
  public issues;
  public fetchedData: Subscription;

  constructor(private service: HttpfetcherService, private dialog: MatDialog) { }

  ngOnInit() {
    this.fetchedData = this.service.getIssues().subscribe(
      (resp: FetchedIssues) => {
        this.totalIssues = resp.total;
        this.leakPeriodIssues = resp.countOnNewCode;
        // Tab Trend
        this.getHistory();
        
        // Issue table
        this.issues = new MatTableDataSource(resp.issues);
        this.issues.sort = this.sort;
        this.issues.paginator = this.paginator;

        // Tab Severity
        resp.severties.forEach(element => {
          this.chartSeverityDataOverall.push(element.count);
          this.chartSeverityDataLeakPeriod.push(element.countOnNewCode);
        });

        // Tab Types
        resp.types.forEach(element => {
          this.chartIssueTypeDataOverall.push(element.count);
          this.chartIssueTypeDataLeakPeriod.push(element.countOnNewCode);
        });

        // Display default leak period issues
        this.chartSeverityData = this.chartSeverityDataLeakPeriod;
        this.chartIssueTypeData = this.chartIssueTypeDataLeakPeriod;
      }
    );
  }

  ngOnDestroy() {
    this.fetchedData.unsubscribe();
  }

  changeSevertyGraph(event: MatRadioChange) {
    if (event.value === GraphType.OVERALL) {
      this.chartSeverityData = this.chartSeverityDataOverall;
    } else {
      this.chartSeverityData = this.chartSeverityDataLeakPeriod;
    }
  }
  changeTypeGraph(event: MatRadioChange) {
    if (event.value === GraphType.OVERALL) {
      this.chartIssueTypeData = this.chartIssueTypeDataOverall;
    } else {
      this.chartIssueTypeData = this.chartIssueTypeDataLeakPeriod;
    }
  }
}
