import { Component, OnInit } from '@angular/core';
import {Project} from '../project';
import {ProjectService} from '../project.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  projects: Project[] = [];

  constructor(private router: Router, private projectService: ProjectService) {
    projectService.getProjects().forEach(value => {
      return this.projects = value.body;
    });
    console.log(this.projects);
  }

  ngOnInit() {
  }

  deleteProject(id: number) {
    this.projectService.deleteProject(id).forEach(value => console.log(value.body));
  }

}
