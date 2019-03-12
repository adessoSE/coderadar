import {Component, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ProjectService} from '../../service/project.service';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {FORBIDDEN, INTERNAL_SERVER_ERROR} from 'http-status-codes';

@Component({
  selector: 'app-main-dashboard',
  templateUrl: './main-dashboard.component.html',
  styleUrls: ['./main-dashboard.component.css']
})
export class MainDashboardComponent implements OnInit {

  projects: Project[] = [];

  constructor(private userService: UserService, private router: Router, private projectService: ProjectService) {}

  ngOnInit(): void {
    this.getProjects();
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
        this.setProjectAnalysisEnabled(newProject);
      }))
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProjects());
        }
    });
  }

  /**
   * Deletes a project from the database.
   * Only works if project is not currently being analyzed.
   * @param project The project to delete
   */
  deleteProject(project: Project): void {
    this.projectService.deleteProject(project.id)
      .then(() => {
        const index = this.projects.indexOf(project, 0);
        if (this.projects.indexOf(project, 0) > -1) {
          this.projects.splice(index, 1);
        }})
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.deleteProject(project));
        }
    });
  }

  /**
   * Sets the analysisActive flag of a project if it has active analyzers.
   * @param project The project.
   */
  setProjectAnalysisEnabled(project: Project): void {
    this.projectService.getAnalyzingJob(project.id)
      .then(response => {
        project.analysisActive = response.body.active;
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.setProjectAnalysisEnabled(project));
        }
      });
  }


  /**
   * Activates or deactivates an analysis on a project.
   * @param project The project.
   */
  toggleProjectAnalysis(project: Project): void {
    if (project.analysisActive) {
      this.projectService.stopAnalyzingJob(project.id)
        .catch(error => {
          if (error.status && error.status === FORBIDDEN) {
            this.userService.refresh().then(() => this.toggleProjectAnalysis(project));
          }
        });
    } else {
      this.projectService.startAnalyzingJob(project.id, false)
        .catch(error => {
          if (error.status && error.status === FORBIDDEN) {
            this.userService.refresh().then(() => this.toggleProjectAnalysis(project));
          }
        });
    }
  }
}
