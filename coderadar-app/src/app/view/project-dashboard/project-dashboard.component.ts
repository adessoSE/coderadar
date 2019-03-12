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
  commits: Commit[] = [];
  project: Project = new Project();

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
        if (e.status === FORBIDDEN) {
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
      this.project = new Project(response.body);
    }).catch(error => {
      if (error.status) {
        if (error.status === FORBIDDEN) {
          this.userService.refresh().then(response => this.getProject());
        } else if (error.status === NOT_FOUND) {
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
