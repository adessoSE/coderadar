import { Injectable } from '@angular/core';
import {ProjectService} from "./project.service";
import {Title} from "@angular/platform-browser";
import {UserService} from "./user.service";
import {Router} from "@angular/router";
import {Project} from "../model/project";
import {FORBIDDEN, NOT_FOUND} from "http-status-codes";
import {AppComponent} from "../app.component";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(private projectService: ProjectService, private titleService: Title, private userService: UserService,
              private router: Router) {
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
}
