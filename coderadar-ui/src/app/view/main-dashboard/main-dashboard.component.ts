import {Component, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ProjectService} from '../../service/project.service';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {FORBIDDEN, UNPROCESSABLE_ENTITY} from 'http-status-codes';
import {Title} from '@angular/platform-browser';
import {AppComponent} from '../../app.component';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-main-dashboard',
  templateUrl: './main-dashboard.component.html',
  styleUrls: ['./main-dashboard.component.scss']
})
export class MainDashboardComponent implements OnInit {

  projects: Project[] = [];

  appComponent = AppComponent;
  waiting = false;

  constructor(private snackBar: MatSnackBar, private titleService: Title, private userService: UserService,
              private router: Router, private projectService: ProjectService) {
    titleService.setTitle('Coderadar - Dashboard');
  }

  ngOnInit(): void {
    this.getProjects();
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
        }
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.deleteProject(project));
        } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
          this.openSnackBar('Cannot delete project! Try again later!', '🞩');
        }
      });
  }

  /**
   * Gets all projects from the project service and constructs a new array of Project objects
   * from the returned JSON. Sends a refresh token if access is denied.
   */
  private getProjects(): void {
    this.waiting = true;
    this.projectService.getProjects()
      .then(response => {response.body.forEach(project => {
        const newProject = new Project(project);
        this.projects.push(newProject);
      });
                         this.waiting = false;
      }
      )
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProjects());
        }
      });
  }

  startAnalysis(id: number) {
    this.projectService.startAnalyzingJob(id, true).then(() => {
      this.openSnackBar('Analysis started!', '🞩');
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh().then(() => this.projectService.startAnalyzingJob(id, true));
      } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        this.openSnackBar('Analysis cannot be started! Try again later!', '🞩');
      }
    });
  }

  resetAnalysis(id: number) {
    this.projectService.resetAnalysis(id, true).then(() => {
      this.openSnackBar('Analysis results deleted!', '🞩');
    }).catch(error => {
      if (error.status && error.status === FORBIDDEN) {
        this.userService.refresh().then(() => this.projectService.startAnalyzingJob(id, true));
      } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
        this.openSnackBar('Analysis results cannot be deleted! Try again later!', '🞩');
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
    });
  }
}
