import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {HttpResponse} from '@angular/common/http';
import {Title} from '@angular/platform-browser';
import {Project} from '../../model/project';
import {AppComponent} from '../../app.component';

@Component({
  selector: 'app-view-commit',
  templateUrl: './view-commit.component.html',
  styleUrls: ['./view-commit.component.scss']
})
export class ViewCommitComponent implements OnInit {

  appComponent = AppComponent;

  commit: Commit = new Commit();
  metrics = [];
  projectId;
  project: Project;


  constructor(private router: Router, private userService: UserService, private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.project = new Project();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.commit.name = params.name;
      this.projectId = params.id;
      this.getCommitInfo();
      this.getProject();
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
      this.projectService.getCommitMetricValues(this.projectId, this.commit.name, metricsArray)
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

  /**
   * Gets the project from the service and saves it in this.project
   */
  private getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.project = new Project(response.body);
        this.titleService.setTitle('Coderadar - ' + this.commit.name.substring(0, 7) + ' - ' +
          AppComponent.trimProjectName(this.project.name));
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }
}
