import {Component, OnInit} from '@angular/core';
import {Project} from '../project';
import {ProjectService} from '../project.service';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent {

  project: Project = new Project();

  constructor(private router: Router, private userService: UserService, private projectService: ProjectService) { }

  submitForm() {
    this.projectService.addProject(this.project).then(response => {
      console.log(response);
      this.project.id = response.body.id;
      console.log(this.project.id);
      this.router.navigate(['/project-configure', this.project.id]);
    }).catch(response => {
      if (response.status) {
        if (response.status === 403) {
          this.userService.refresh().then(r => this.submitForm());
        }
      }
    });
  }
}
