import {Component, OnInit} from '@angular/core';
import {Project} from '../project';
import {ProjectService} from '../project.service';
import {Router} from '@angular/router';
import {UserService} from '../user.service';
import {hasLifecycleHook} from '@angular/compiler/src/lifecycle_reflector';

@Component({
  selector: 'app-main-dashboard',
  templateUrl: './main-dashboard.component.html',
  styleUrls: ['./main-dashboard.component.css']
})
export class MainDashboardComponent implements OnInit {

  projects: Project[] = [];

  constructor(private userService: UserService, private router: Router, private projectService: ProjectService) {
  }

  updateProjectsList() {
    this.projectService.getProjects().then(
      response => {
        this.projects = response.body;
        this.projects.forEach(p => {
          if (p.startDate !== null) {
            p.startDate = new Date(p.startDate[0], p.startDate[1] - 1, p.startDate[2]).toDateString();
          } else {
            p.startDate = 'first commit';
          }

          if (p.endDate !== null) {
            p.endDate = new Date(p.endDate[0], p.endDate[1] - 1, p.endDate[2]).toDateString();
          } else {
            p.endDate = 'current';
          }
        });
        this.checkProjectsStatus();
      }
    ).catch(e => {
      console.log(e);
      if (e.status) {
        if (e.status === 403) {
          this.userService.refresh().then( (() => this.updateProjectsList()));
        }
      }
    });
  }

  checkProjectsStatus() {
    this.projects.forEach(project => {
      this.projectService.getAnalyzingJob(project.id).then(response => {
        console.log(response.body);
        if (response.body.active === true) {
          project.analysisStatus = 'running';
        } else {
          project.analysisStatus = 'complete';
        }
      }).catch(error => {
        console.log(error);
        if (error.status) {
          if (error.status === 403) {
            this.userService.refresh().then(() => this.checkProjectsStatus());
          } else if (error.status === 404) {
            project.analysisStatus = 'not running';
          }
        }
      });
    });
  }

  removeProject(project: Project) {
    this.projectService.deleteProject(project.id).then(response => {
      const index = this.projects.indexOf(project, 0);
      if (index > -1) {
        this.projects.splice(index, 1);
      }
    }).catch(error => {
      console.log(error);
      if (error.status) {
        if (error.status === 403) {
          this.userService.refresh().then(() => this.removeProject(project));
        }
      }
    });
  }

  ngOnInit(): void {
    this.updateProjectsList();
  }
}
