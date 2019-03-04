import {Component} from '@angular/core';
import {Project} from '../project';
import {ProjectService} from '../project.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-main-dashboard',
  templateUrl: './main-dashboard.component.html',
  styleUrls: ['./main-dashboard.component.css']
})
export class MainDashboardComponent {

  projects: Project[] = [];

  constructor(private router: Router, private projectService: ProjectService) {
    projectService.getProjects(' ').toPromise().then(data => {
      this.projects = data.body;
      this.projects.forEach(p => {
        p.startDate = new Date(p.startDate[0], p.startDate[1] - 1, p.startDate[2]);
        p.endDate = new Date(p.endDate[0], p.endDate[1] - 1, p.endDate[2]);
      });
    }).catch(e => {
      if (e.hasOwnProperty('status')) {
        if (e.status === 403) {
          this.router.navigate(['/login']);
        }
      }
    });
  }

  removeProject(project: Project) {
    this.projectService.deleteProject(project.id, ' ').toPromise().then(response => console.log(response));
    const index = this.projects.indexOf(project, 0);
    if (index > -1) {
      this.projects.splice(index, 1);
    }
  }
}
