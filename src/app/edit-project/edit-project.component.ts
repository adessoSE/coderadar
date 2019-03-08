import { Component, OnInit } from '@angular/core';
import {Project} from '../project';
import {AnalyzerConfiguration} from '../analyzer-configuration';
import {FilePatterns} from '../file-patterns';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../user.service';
import {ProjectService} from '../project.service';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
  }

  project: Project = new Project();
  projectName = '';
  private projectId: any;

  incorrectURL = false;
  projectExists = false;
  nameEmpty = false;

  submitForm() {
    this.incorrectURL = false;
    this.projectExists = false;
    this.nameEmpty = false;

    this.incorrectURL = this.project.vcsUrl.trim().length === 0;
    this.nameEmpty = this.project.name.trim().length === 0;

    if (this.nameEmpty || this.incorrectURL) {
      return;
    }

    this.projectService.editProject(this.project).then(response => {
      console.log(response);
      this.router.navigate(['/dashboard']);
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

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getProject();
    });
  }

  private getProject() {
    this.projectService.getProject(this.projectId).then(response => {
      if (response.body.startDate != null) {
        response.body.startDate = new Date(response.body.startDate[0],
          response.body.startDate[1] - 1, response.body.startDate[2] + 1).toISOString().split('T')[0];
      } else {
        response.body.startDate = null;
      }
      if (response.body.endDate != null) {
        response.body.endDate = new Date(response.body.endDate[0],
          response.body.endDate[1] - 1, response.body.endDate[2] + 1).toISOString().split('T')[0];
        console.log(response.body.endDate);
      } else {
        response.body.endDate = null;
      }
      this.projectName = response.body.name;
      this.project = response.body;
    }).catch(error => {
      console.log(error);
      if (error.status) {
        if (error.status === 403) {
          this.userService.refresh().then(response => this.getProject());
        } else if (error.status === 404) {
          this.router.navigate(['/dashboard']);
        }
      }});
  }
}
