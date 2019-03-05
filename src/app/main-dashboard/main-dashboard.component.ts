import {Component} from '@angular/core';
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
export class MainDashboardComponent {

  projects: Project[] = [];

  constructor(private userService: UserService, private router: Router, private projectService: ProjectService) {
    this.updateProjectsList();
  }

  updateProjectsList() {
    this.projectService.getProjects().then(
      response => {
        this.projects = response.body;
        this.projects.forEach(p => {
          p.startDate = new Date(p.startDate[0], p.startDate[1] - 1, p.startDate[2]);
          p.endDate = new Date(p.endDate[0], p.endDate[1] - 1, p.endDate[2]);
        }); }
    ).catch(e => {
      console.log(e);
      if (e.status) {
        if (e.status === 403) {
          this.userService.refresh().then( (() => this.updateProjectsList()));
        }
      }
    });
  }

  removeProject(project: Project) {
    this.projectService.deleteProject(project.id).then(response => console.log(response));
    const index = this.projects.indexOf(project, 0);
    if (index > -1) {
      this.projects.splice(index, 1);
    }
  }
}
