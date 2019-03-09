import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {

  oldPassword = '';
  newPassword = '';
  newPasswordConfirm = '';

  passwordsDoNotMatch = false;
  invalidPassword = false;
  currentPasswordWrong = false;

  constructor(private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getLoggedInUser();
  }

  submitForm() {
    this.passwordsDoNotMatch = false;
    this.invalidPassword = false;
    this.currentPasswordWrong = false;

    if (this.newPassword !== this.newPasswordConfirm) {
      this.passwordsDoNotMatch = true;
    }
    if (this.newPassword.length < 8) {
      this.invalidPassword = true;
    } else {
      this.userService.login(this.userService.getLoggedInUser().username, this.oldPassword) // authenticate with the current password
        .then(() => this.userService.changeUserPassword(this.newPassword) // change the password
          .then(() => { // login again to refresh the token and navigate to the dashboard when done
            this.router.navigate(['/dashboard']);
            this.userService.login(this.userService.getLoggedInUser().username, this.newPassword);
          })
          .catch(() => this.invalidPassword = true))
        .catch(() => this.currentPasswordWrong = true);
    }
  }
}
