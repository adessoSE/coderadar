import {Component, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {BAD_REQUEST, FORBIDDEN, INTERNAL_SERVER_ERROR, NOT_FOUND} from 'http-status-codes';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {

  private projectId: number;

  projectName: string;
  project: Project;

  // Error fields
  incorrectURL = false;
  projectExists = false;
  nameEmpty = false;

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.project = new Project();
    this.projectName = '';
  }


  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getProject();
    });
  }

  /**
   * Gets the project from the service and saves it this.project.
   * If access is denied (403) sends the refresh token and tries to submit again.
   * If the project does not exists (404) redirects to the dashboard.
   */
  private getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.project = new Project(response.body);
        this.projectName = this.project.name;
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
            this.userService.refresh().then(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  /**
   * Called when the form is submitted.
   * Does input validation and calls ProjectService.editProject().
   * Handles errors from the server.
   * If access is denied (403) sends the refresh token and tries to submit again.
   */
  submitForm(): void {
    console.log(!this.validateInput());
    if (!this.validateInput()) {
      this.projectService.editProject(this.project)
        .then(() => this.router.navigate(['/dashboard']))
        .catch(error => {
          console.log(error);
          if (error.status && error.status === FORBIDDEN) {
              this.userService.refresh().then(() => this.submitForm());
          } else if (error.status && error.status === BAD_REQUEST) {
            if (error.error && error.error.errorMessage === 'Validation Error') {
              error.error.fieldErrors.forEach(field => {
                if (field.field === 'vcsUrl') {
                  this.incorrectURL = true;
                }
              });
            }
          } else if (error.status === INTERNAL_SERVER_ERROR &&
            error.error.errorMessage === 'Project with name \'' + this.project.name + '\' already exists. Please choose another name.') {
            this.projectExists = true;
          }
      });
    }
  }

  /**
   * Checks for empty form fields.
   */
  private validateInput(): boolean {
    this.incorrectURL = false;
    this.projectExists = false;
    this.nameEmpty = false;

    this.incorrectURL = this.project.vcsUrl.trim().length === 0;
    this.nameEmpty = this.project.name.trim().length === 0;

    if (this.project.startDate === 'first commit') {
      this.project.startDate = null;
    }

    if (this.project.endDate === 'current') {
      this.project.endDate = null;
    }

    return this.nameEmpty || this.incorrectURL;
  }
}
