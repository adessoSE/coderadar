import {Component, OnInit} from '@angular/core';
import {Project} from '../project';
import {ProjectService} from '../project.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {

  project: Project = new Project();

  constructor(private router: Router, private projectService: ProjectService) { }

  ngOnInit() {
  }

  submitForm() {
    this.projectService.addProject(this.project).then(response => console.log(response));
    this.router.navigate(['/dashboard']);
  }
}
