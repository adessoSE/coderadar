import { Injectable } from '@angular/core';
import {ProjectService} from "./project.service";
import {Title} from "@angular/platform-browser";
import {UserService} from "./user.service";
import {Router} from "@angular/router";
import {Project} from "../model/project";
import {FORBIDDEN, NOT_FOUND, UNPROCESSABLE_ENTITY} from "http-status-codes";
import {AppComponent} from "../app.component";
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(private projectService: ProjectService, private titleService: Title, private userService: UserService,
              private router: Router, private snackBar: MatSnackBar) {
  }

  /**
   * Gets the project from the service and saves it this.project.
   * If access is denied (403) sends the refresh token and tries to submit again.
   * If the project does not exists (404) redirects to the dashboard.
   */
  public getProject(title: string, projectId: number): Promise<any> {
    return this.projectService.getProject(projectId)
      .then(response => {
        const project = new Project(response.body);
        this.titleService.setTitle(title + ' ' + AppComponent.trimProjectName(project.name));
        return project;
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getProject(title, projectId));
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  public startAnalysis(id: number, branch: string, ) {
    return this.projectService.startAnalyzingJob(id, branch).then(() => {
      this.openSnackBar('Analysis started!', '🞩');
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.projectService.startAnalyzingJob(id, 'master'));
      } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        if (error.error.errorMessage === 'Cannot analyze project without analyzers') {
          this.openSnackBar('Cannot analyze, no analyzers configured for this project!', '🞩');
        } else if (error.error.errorMessage === 'Cannot analyze project without file patterns') {
          this.openSnackBar('Cannot analyze, no file patterns configured for this project!', '🞩');
        } else {
          this.openSnackBar('Analysis cannot be started! Try again later!', '🞩');
        }
      }
    });
  }

  resetAnalysis(id: number) {
    this.projectService.resetAnalysis(id).then(() => {
      this.openSnackBar('Analysis results deleted!', '🞩');
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.projectService.resetAnalysis(id));
      } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        this.openSnackBar('Analysis results cannot be deleted! Try again later!', '🞩');
      }
    });
  }

  stopAnalysis(id: number) {
    this.projectService.stopAnalyzingJob(id).then(() => {
      this.openSnackBar('Analysis stopped!', '🞩');
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh(() => this.projectService.stopAnalyzingJob(id));
      } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        this.openSnackBar('Analysis stopped!', '🞩');
      }
    });
  }

  private openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
    });
  }
}
