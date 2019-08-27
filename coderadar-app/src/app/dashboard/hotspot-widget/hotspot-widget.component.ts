
import { Observable, Subscription } from 'rxjs';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpfetcherService } from '../services/httpfetcher.service';

import { ProjectService } from 'src/app/service/project.service';
import { Commit } from 'src/app/model/commit';
import { Project } from 'src/app/model/project';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { FORBIDDEN } from 'http-status-codes';
import { IFileNode } from '../interfaces/metric';

@Component({
  selector: 'app-hotspot-widget',
  templateUrl: './hotspot-widget.component.html',
  styleUrls: ['./hotspot-widget.component.scss']
})
export class HotspotWidgetComponent implements OnInit, OnDestroy {
  fileNodeMetrics: IFileNode[] = new Array();
  rowNodeHeadings: string[] = ['name'];
  commit: Commit = new Commit();
  metrics = [];
  projectId;
  project: Project;

  // TODO Delete
  fileMetricSubsciption: Subscription;
  fileMetricObserver = {
    next: (resp: any) => {
      resp.children.forEach((element: IFileNode) => {
        this.fileNodeMetrics.push(element);
        if (this.rowNodeHeadings.length === 1) {
          Object.getOwnPropertyNames(element.metrics).forEach(heading => this.rowNodeHeadings.push(heading));
        }
      });
    },
    error: (err: any) => console.log(err)
  };
  // End Delete

  constructor(public dialog: MatDialog, private httpFetcher: HttpfetcherService, private router: Router, private projectService: ProjectService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    //this.fileMetricSubsciption = this.httpFetcher.getHotspotData().subscribe(this.fileMetricObserver);
    this.route.params.subscribe(params => {
      this.commit.name = params.name;
      this.projectId = params.id;
      this.getCommitInfo();
    });
  }

  ngOnDestroy() {
    this.fileMetricSubsciption.unsubscribe();
  }

  openHotspotConfigurationDialog() {

  }

  private getCommitInfo(): void {
    this.getCommits();

    this.getMetrics().then(response => {
      const metricsArray: string[] = ['coderadar:size:cloc:java'];
      this.projectService.getTree(this.commit.name, metricsArray, this.projectId).then(resp => {
        resp.body.children.forEach((element: IFileNode) => {
          this.fileNodeMetrics.push(element);
          if (this.rowNodeHeadings.length === 1) {
            Object.getOwnPropertyNames(element.metrics).forEach(heading => this.rowNodeHeadings.push(heading));
          }
        });
      }
      );
    }).catch(e => {
      if (e.status && e.status === FORBIDDEN) {
        console.log(e);
      }
    });
  }
  /**
   * Calls ProjectService.getAvailableMetrics with the current project id.
   */
  private getMetrics(): Promise<HttpResponse<any>> {
    return this.projectService.getAvailableMetrics(this.projectId);
  }

  commits = [];

  /**
   * Gets all commits for this project from the service and saves them in this.commits.
   */
  private getCommits(): void {
    this.projectService.getCommits(this.projectId)
      .then(response => {
        this.commits = response.body;

        this.commits.sort((a, b) => {
          if (a.timestamp === b.timestamp) {
            return 0;
          } else if (a.timestamp > b.timestamp) {
            return -1;
          } else {
            return 1;
          }
        });
      })
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {

        }
      });
  }

}
