import {Component, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {BAD_REQUEST, CONFLICT, FORBIDDEN, INTERNAL_SERVER_ERROR, NOT_FOUND, UNPROCESSABLE_ENTITY} from 'http-status-codes';
import {Title} from '@angular/platform-browser';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.scss']
})
export class EditProjectComponent implements OnInit {

  projectName: string;
  // because coderadar does not check if the project name exists already
  projects: Project[] = [];

  // I need all project at the moment,
  project: Project;
  // Error fields
  incorrectURL = false;
  projectExists = false;
  nameEmpty = false;
  projectId: number;

  constructor(private snackBar: MatSnackBar, private router: Router, private userService: UserService,  private titleService: Title,
              private projectService: ProjectService, private route: ActivatedRoute) {
    this.project = new Project();
    this.projectName = '';
  }


  ngOnInit(): void {
    this.getProjects();
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getProject();
    });
  }

  /**
   * Called when the form is submitted.
   * Does input validation and calls ProjectService.editProject().
   * Handles errors from the server.
   * If access is denied (403) sends the refresh token and tries to submit again.
   */
  submitForm(): void {
    if (!this.validateInput()) {
      console.log(this.project);
      this.projectService.editProject(this.project)
        .then(() => {
          this.router.navigate(['/dashboard']);
          this.openSnackBar('Project successfully edited!', '🞩');
        })
        .catch(error => {
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
          } else if (error.status === CONFLICT &&
            error.errorMessage === 'Project with name \'' + this.project.name + '\' already exists. Please choose another name.') {
            this.projectExists = true;
          } else if (error.status === UNPROCESSABLE_ENTITY) {
            this.openSnackBar('Project cannot be edited! Try again later!', '🞩');
          }
        });
    }
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
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
        this.titleService.setTitle('Coderadar - Edit ' + this.projectName);
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
   * Checks for empty form fields.
   */
  private validateInput(): boolean {
    this.incorrectURL = this.project.vcsUrl.trim().length === 0;
    this.nameEmpty = this.project.name.trim().length === 0;
    this.projectExists = this.projects.filter(p => (p.name === this.project.name) && p.name !== this.projectName).length !== 0;


    if (this.project.startDate === 'first commit') {
      this.project.startDate = null;
    }

    if (this.project.endDate === 'current') {
      this.project.endDate = null;
    }

    return this.nameEmpty || this.incorrectURL || this.projectExists;
  }


  /**
   * Gets all projects from the project service and constructs a new array of Project objects
   * from the returned JSON. Sends a refresh token if access is denied.
   */
  private getProjects(): void {
    this.projectService.getProjects()
      .then(response => response.body.forEach(project => {
        const newProject = new Project(project);
        this.projects.push(newProject);
      }))
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProjects());
        }
      });
  }
}
