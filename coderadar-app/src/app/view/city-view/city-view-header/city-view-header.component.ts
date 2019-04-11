import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../service/user.service';
import {AppEffects} from '../../../city-map/shared/effects';
import {Project} from '../../../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {ProjectService} from '../../../service/project.service';
import {Router} from '@angular/router';
import {AppComponent} from '../../../app.component';

@Component({
  selector: 'app-city-view-header',
  templateUrl: './city-view-header.component.html',
  styleUrls: ['./city-view-header.component.css']
})
export class CityViewHeaderComponent implements OnInit {
  appComponent = AppComponent;

  projectId: number;
  project: Project;

  constructor(private userService: UserService, private appEffects: AppEffects,
              private projectService: ProjectService, private router: Router) {
    this.project = new Project();
  }

  ngOnInit() {
    this.projectId = this.appEffects.currentProjectId;
    this.getProject();
  }

  /**
   * Gets the project from the service and saves it in this.project
   */
  private getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.project = new Project(response.body);
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }


  logout(): void {
    this.userService.logout();
  }

  /**
   * Gets the current username from the user service.
   */
  getUsername(): string {
    return UserService.getLoggedInUser().username;
  }
}
