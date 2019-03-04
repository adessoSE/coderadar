import { Component } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import {Project} from '../project';
import {ProjectService} from '../project.service';

@Component({
  selector: 'app-main-dashboard',
  templateUrl: './main-dashboard.component.html',
  styleUrls: ['./main-dashboard.component.css']
})
export class MainDashboardComponent {

  projects: Project[] = [];

  constructor(private projectService: ProjectService, private breakpointObserver: BreakpointObserver) {
    projectService.getProjects().toPromise().then(data => {
      this.projects = data.body;
      this.projects.forEach(p => {
        p.startDate = new Date(p.startDate[0], p.startDate[1] - 1, p.startDate[2]);
        p.endDate = new Date(p.endDate[0], p.endDate[1] - 1, p.endDate[2]);
      });
    }
  );
  }
}
