import {HotspotConfigurationComponent} from '../dialogs/hotspot-configuration/hotspot-configuration.component';
import {Subscription} from 'rxjs';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {HttpfetcherService} from '../services/httpfetcher.service';

import {ProjectService} from 'src/app/service/project.service';
import {Commit} from 'src/app/model/commit';
import {Project} from 'src/app/model/project';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {FORBIDDEN} from 'http-status-codes';
import {IFileNode} from '../interfaces/metric';

import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material';

@Component({
  selector: 'app-hotspot-widget',
  templateUrl: './hotspot-widget.component.html',
  styleUrls: ['./hotspot-widget.component.scss']
})
export class HotspotWidgetComponent implements OnInit, OnDestroy {
  @ViewChild(MatSort, {}) sort: MatSort;
  @ViewChild(MatPaginator, {}) paginator: MatPaginator;

  constructor(public dialog: MatDialog, private httpFetcher: HttpfetcherService,
              private projectService: ProjectService, private route: ActivatedRoute) { }

  configDialog: MatDialogRef<HotspotConfigurationComponent>;

  public fileMetrics: IFileNode[] = [];
  rowHeadings: string[] = ['name'];
  commits = [];
  commit: Commit = new Commit();
  availableMetrics: string[] = [];
  metrics = [];
  projectId;
  project: Project;
  public dataSource;
  fileMetricSubsciption: Subscription;

  /**
   * Fill table with content
   */
  fileMetricObserver = {
    next: (resp: any) => {
      resp.metrics.forEach(heading => {
        this.rowHeadings.push(heading.metricName)
      });
      resp.children.forEach((element: IFileNode) => {
        element.name = this.formatFileString(element.name);
        this.fileMetrics.push(element);
      });
      this.dataSource = new MatTableDataSource(this.fileMetrics);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sortingDataAccessor = this.sortingDataAccessor;
    },
    error: (err: any) => console.log(err)
  };



  /**
   * Concat filename
   * @param metricString string
   */
  formatFileString(metricString: string): string {
    const partArray = metricString.split('/');
    const lastIndex = partArray.length - 1;
    return partArray[lastIndex];
  }

  sortingDataAccessor(item, property) {
    return item.metrics.find(value => value.metricName == property).value;
  }

  ngOnInit() {
    // this.fileMetricSubsciption = this.httpFetcher.getHotspotData().subscribe(this.fileMetricObserver);
    this.route.params.subscribe(params => {
      this.commit.name = params.name;
      this.projectId = params.id;
      this.getMetrics().then(response => {
        response.body.forEach(m => this.availableMetrics.push(m));
      }).catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          console.log(e);
        }
      });
      const metricsArray: string[] = [
        'coderadar:size:sloc:java',
        'checkstyle:com.puppycrawl.tools.checkstyle.checks.design.InnerTypeLastCheck'
      ];
      this.updateHotspotTable(metricsArray);
    });
  }

  ngOnDestroy() {
    this.fileMetricSubsciption.unsubscribe();
  }

  /**
   * Open an configuration dialog to choose metrics
   */
  openHotspotConfigurationDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      projectId: this.projectId,
      availableMetrics: this.availableMetrics
    };
    this.configDialog = this.dialog.open(HotspotConfigurationComponent, dialogConfig);
    this.configDialog.afterClosed().subscribe(result => {
      this.updateHotspotTable(result);
    });
  }

  /**
   * Update table with choosen metrics
   * @param metrics: string[]
   */
  private updateHotspotTable(metrics: string[]) {
    this.fileMetrics = [];
    this.rowHeadings = ['name'];
    this.fileMetricSubsciption = this.projectService.getTree(this.commit.name, metrics, this.projectId).subscribe(this.fileMetricObserver);
  }

  /**
   * Calls ProjectService.getAvailableMetrics with the current project id.
   */
  private getMetrics(): Promise<HttpResponse<any>> {
    return this.projectService.getAvailableMetrics(this.projectId);
  }

  getMetricValueForElement(element: any, heading: string) {
    const metric = element.metrics.find(value => value.metricName == heading);
    if(metric != undefined){
      return metric.value;
    }
    return null;
  }
}
