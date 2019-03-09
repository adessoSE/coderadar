import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../user.service';
import {ProjectService} from '../project.service';
import {Commit} from '../commit';
import {Project} from '../project';

@Component({
  selector: 'app-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  styleUrls: ['./project-dashboard.component.css']
})
export class ProjectDashboardComponent implements OnInit {

  projectId;
  commits: Commit[] = [];
  project: Project;

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
  }


  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getCommits();
      this.getProject();
    });
  }

  private getCommits() {
    this.projectService.getCommits(this.projectId).then(response => {
      this.commits = response.body;
      this.commits.reverse();
    }).catch(e => {
      if (e.status) {
        if (e.status === 403) {
          this.userService.refresh().then( (() => this.getCommits()));
        }
      }
    });
  }

  booleanToString(arg: boolean) {
    if (arg) {
      return 'yes';
    } else {
      return 'no';
    }
  }

  timestampToDate(timestamp: number) {
    return new Date(timestamp).toLocaleDateString();
  }

  private getProject() {
    this.projectService.getProject(this.projectId).then(response => {
      if (response.body.startDate != null) {
        response.body.startDate = new Date(response.body.startDate[0],
          response.body.startDate[1] - 1, response.body.startDate[2] + 1).toISOString().split('T')[0];
      } else {
        response.body.startDate = null;
      }
      if (response.body.endDate != null) {
        response.body.endDate = new Date(response.body.endDate[0],
          response.body.endDate[1] - 1, response.body.endDate[2] + 1).toISOString().split('T')[0];
      } else {
        response.body.endDate = null;
      }
      this.project = response.body;
    }).catch(error => {
      if (error.status) {
        if (error.status === 403) {
          this.userService.refresh().then(response => this.getProject());
        } else if (error.status === 404) {
          this.router.navigate(['/dashboard']);
        }
      }});
  }

  getTitleText() {
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
