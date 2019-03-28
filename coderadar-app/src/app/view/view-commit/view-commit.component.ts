import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {FORBIDDEN} from 'http-status-codes';
import {HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-view-commit',
  templateUrl: './view-commit.component.html',
  styleUrls: ['./view-commit.component.scss']
})
export class ViewCommitComponent implements OnInit {

  commit: Commit = new Commit();
  metrics = [];
  projectId;

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.commit.name = params.name;
      this.projectId = params.id;
      this.getCommitInfo();
    });
  }

  /**
   * Gets the values for all the metrics in this project and
   * saves them in this.metrics;
   */
  private getCommitInfo(): void {
    this.getMetrics().then(response => {
      const metricsArray: string[] = [];
      response.body.forEach(m => metricsArray.push(m.metricName));
      this.projectService.getCommitsMetricValues(this.projectId, this.commit.name, metricsArray)
        .then(res => {
          for (const i in res.body.metrics) {
            if (res.body.metrics[i]) {
              this.metrics.push([i, res.body.metrics[i]]);
            }
          }
        })
        .catch(e => {
          if (e.status && e.status === FORBIDDEN) {
            this.userService.refresh().then((() => this.getCommitInfo()));
          }
        });
    }).catch(e => {
      if (e.status && e.status === FORBIDDEN) {
        this.userService.refresh().then((() => this.getCommitInfo()));
      }
    });
  }

  /**
   * Calls ProjectService.getAvailableMetrics with the current project id.
   */
  private getMetrics(): Promise<HttpResponse<any>> {
    return this.projectService.getAvailableMetrics(this.projectId);
  }
}
