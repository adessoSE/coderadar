import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {UserService} from '../../../service/user.service';
import {Project} from '../../../model/project';
import {AppComponent} from '../../../app.component';

@Component({
  selector: 'app-city-view-header',
  templateUrl: './city-view-header.component.html',
  styleUrls: ['./city-view-header.component.css']
})
export class CityViewHeaderComponent implements OnInit, OnChanges {
  appComponent = AppComponent;

  projectId = 0;
  projectName = '';

  @Input() project: Project;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
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

  ngOnChanges(changes: SimpleChanges): void {
    if (this.project !== undefined) {
      this.projectId = this.project.id;
      this.projectName = this.project.name;
    }
  }
}
