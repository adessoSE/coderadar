import { Component, OnInit } from '@angular/core';
import {Project} from '../project';
import {ProjectService} from '../project.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  projects: Project[] = [];

  constructor(private projectService: ProjectService) {
    projectService.getProjects().forEach(value => this.projects.push(value.body));
    console.log(this.projects);
  }

  ngOnInit() {
  }

}
