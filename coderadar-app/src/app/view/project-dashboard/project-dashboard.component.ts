import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {Project} from '../../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {Title} from '@angular/platform-browser';
import {AppComponent} from '../../app.component';


@Component({
  selector: 'app-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  styleUrls: ['./project-dashboard.component.scss']
})
export class ProjectDashboardComponent implements OnInit {

  appComponent = AppComponent;

  projectId;
  commits: Commit[];
  commitsAnalyzed = 0;
  project: Project;

  constructor(private router: Router, private userService: UserService, private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.project = new Project();
    this.commits = [];
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getCommits();
      this.getProject();
    });
  }

  /**
   * Gets all commits for this project from the service and saves them in this.commits.
   */
  private getCommits(): void {
    this.projectService.getCommits(this.projectId)
      .then(response => {
        this.commits = response.body;
        this.commits.forEach(c => {
          if (c.analyzed) {
            this.commitsAnalyzed++;
          }
        });
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
          this.userService.refresh().then((() => this.getCommits()));
        }
      });
  }

  /**
   * Gets the project from the service and saves it in this.project
   */
  private getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.project = new Project(response.body);
        this.titleService.setTitle('Coderadar - ' + AppComponent.trimProjectName(this.project.name));
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }


  filterAnalyzedCommits(): Commit[] {
    return this.commits.filter(value => value.analyzed);
  }
}
