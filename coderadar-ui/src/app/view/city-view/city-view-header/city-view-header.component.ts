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
    this.project = this.appEffects.currentProject;
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
