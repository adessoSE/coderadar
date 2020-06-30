import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {FORBIDDEN} from 'http-status-codes';
import {HttpResponse} from '@angular/common/http';
import {Title} from '@angular/platform-browser';
import {Project} from '../../model/project';
import {AppComponent} from '../../app.component';
import {MetricValue} from '../../model/metric-value';
import {UtilsService} from '../../service/utils.service';

@Component({
  selector: 'app-view-commit',
  templateUrl: './view-commit.component.html',
  styleUrls: ['./view-commit.component.scss']
})
export class ViewCommitComponent implements OnInit {

  appComponent = AppComponent;

  commit: Commit = new Commit();
  metrics: MetricValue[] = [];
  projectId;
  project: Project;
  waiting = false;


  constructor(private router: Router, private userService: UserService, private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute, private utilsService: UtilsService) {
    this.project = new Project();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.commit.name = params.name;
      this.projectId = params.id;
      this.getCommitInfo();
      this.utilsService.getProject('Coderadar - ' + this.commit.name.substring(0, 7) + ' -' , this.projectId).then(project => {
        this.project = project;
      });
    });
  }

  /**
   * Gets the values for all the metrics in this project and
   * saves them in this.metrics;
   */
  private getCommitInfo(): void {
    this.waiting = true;
    this.getMetrics().then(response => {
      this.projectService.getCommitsMetricValues(this.projectId, this.commit.name, response.body)
        .then(res => {
          this.waiting = false;
          this.metrics = res.body;
        })
        .catch(e => {
          if (e.status && e.status === FORBIDDEN) {
            this.userService.refresh(() => this.getCommitInfo());
          }
        });
    }).catch(e => {
      if (e.status && e.status === FORBIDDEN) {
        this.userService.refresh(() => this.getCommitInfo());
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
