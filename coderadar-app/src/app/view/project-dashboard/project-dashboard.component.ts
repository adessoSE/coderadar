import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {Commit} from '../../model/commit';
import {Project} from '../../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';

@Component({
  selector: 'app-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  styleUrls: ['./project-dashboard.component.css']
})
export class ProjectDashboardComponent implements OnInit {

  projectId;
  commits: Commit[];
  project: Project;

  constructor(private router: Router, private userService: UserService,
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
        this.commits.reverse(); })
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh().then( (() => this.getCommits()));
        }
    });
  }

  /**
   * Return 'yes' for true and 'no' for false.
   * @param arg boolean
   */
  booleanToString(arg: boolean): string {
    if (arg) {
      return 'yes';
    } else {
      return 'no';
    }
  }

  /**
   * Constructs a date from a timestamp and returns it in string form.
   * @param timestamp The timestamp to construct a date from.
   */
  timestampToDate(timestamp: number): string {
    return new Date(timestamp).toLocaleDateString();
  }

  /**
   * Gets the project from the service and saves it in this.project
   */
  private getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => this.project = new Project(response.body))
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
            this.userService.refresh().then(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  /**
   * Formats the title text according to the project start and end dates.
   */
  getTitleText(): string {
    if (this.project.startDate == null && this.project.endDate == null) {
      return 'Showing all commits for project ' + this.project.name;
    } else if (this.project.startDate != null && this.project.endDate == null) {
      return 'Showing all commits for project ' + this.project.name + ' from ' + this.project.startDate + ' up until today';
    } else if (this.project.startDate == null && this.project.endDate != null) {
      return 'Showing all commits for project ' + this.project.name + ' from '
        + new Date(this.commits[this.commits.length - 1].timestamp).toLocaleDateString() + ' up until ' + this.project.endDate;
    } else {
      return 'Showing all commits for project ' + this.project.name + ' from '
        + this.project.startDate + ' up until ' + this.project.endDate;
    }
  }
}
