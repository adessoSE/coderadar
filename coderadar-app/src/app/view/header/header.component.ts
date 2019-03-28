import {Component, Input, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {

  @Input()
  title: string;

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
