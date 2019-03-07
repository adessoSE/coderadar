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

  incorrectURL = false;
  projectExists = false;
  nameEmpty = false;


  constructor(private router: Router, private userService: UserService, private projectService: ProjectService) {
    this.project.name = '';
    this.project.vcsUrl = '';
  }

  submitForm() {
    this.incorrectURL = false;
    this.projectExists = false;
    this.nameEmpty = false;

    this.incorrectURL = this.project.vcsUrl.trim().length === 0;
    this.nameEmpty = this.project.name.trim().length === 0;

    if (this.nameEmpty || this.incorrectURL) {
      return;
    }

    this.projectService.addProject(this.project).then(response => {
      console.log(response);
      this.project.id = response.body.id;
      console.log(this.project.id);
      this.router.navigate(['/project-configure', this.project.id]);
    }).catch(response => {
      if (response.status) {
        console.log(response);
        if (response.status === 403) {
          this.userService.refresh().then(r => this.submitForm());
        } else if (response.status === 400) {
          if (response.error && response.error.errorMessage === 'Validation Error') {
            response.error.fieldErrors.forEach(field => {
              if (field.field === 'vcsUrl') {
                this.incorrectURL = true;
              }
            });
          }
        } else if (response.status === 500 &&
          response.error.errorMessage === 'Project with name \'' + this.project.name + '\' already exists. Please choose another name.') {
            this.projectExists = true;
        }
      }
    });
  }
}
