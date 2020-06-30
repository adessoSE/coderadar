import {Component, Inject, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ProjectService} from '../../service/project.service';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {FORBIDDEN, UNPROCESSABLE_ENTITY} from 'http-status-codes';
import {Title} from '@angular/platform-browser';
import {AppComponent} from '../../app.component';
import {MatDialog, MatSnackBar} from '@angular/material';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {UtilsService} from '../../service/utils.service';

@Component({
  selector: 'app-main-dashboard',
  templateUrl: './main-dashboard.component.html',
  styleUrls: ['./main-dashboard.component.scss']
})
export class MainDashboardComponent implements OnInit {

  projects: Project[] = [];

  dialogRef: MatDialogRef<ConfirmDeleteProjectDialogComponent>;
  appComponent = AppComponent;
  waiting = false;

  constructor(private snackBar: MatSnackBar, private titleService: Title, private userService: UserService,
              private router: Router, private projectService: ProjectService, private dialog: MatDialog,
              private utilsService: UtilsService) {
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
          this.userService.refresh(() => this.deleteProject(project));
        } else if (error.status && error.status === UNPROCESSABLE_ENTITY) {
          this.openSnackBar('Cannot delete project! Try again later!', '🞩');
        }
      });
  }

  openProjectDeletionDialog(projectToBeDeleted: Project) {
    this.dialogRef = this.dialog.open(ConfirmDeleteProjectDialogComponent, {
      data: {
        project: projectToBeDeleted
      }
    });

    this.dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deleteProject(result);
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
          this.userService.refresh(() => this.getProjects());
        }
      });
  }

  startAnalysis(id: number) {
    this.utilsService.startAnalysis(id, 'master');
  }

  resetAnalysis(id: number) {
    this.utilsService.resetAnalysis(id);
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
    });
  }

  stopAnalysis(id: number) {
    this.utilsService.stopAnalysis(id);
  }
}

class DeleteProjectDialogData {
  project: Project;
}

@Component({
  selector: 'app-delete-project-dialog',
  templateUrl: 'delete-project-dialog.html'
})
export class ConfirmDeleteProjectDialogComponent{

  constructor(
    public dialogRef: MatDialogRef<ConfirmDeleteProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteProjectDialogData
  ) {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
