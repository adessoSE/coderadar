import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../service/user.service';

@Component({
  selector: 'app-city-view-header',
  templateUrl: './city-view-header.component.html',
  styleUrls: ['./city-view-header.component.css']
})
export class CityViewHeaderComponent implements OnInit {

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

}
