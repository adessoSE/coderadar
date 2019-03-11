import {Component, OnInit} from '@angular/core';
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

  constructor(private router: Router, private userService: UserService) {}

  ngOnInit(): void {
    UserService.getLoggedInUser();
  }

  /**
   * Is called upon form submission. Validates user input and calls
   * UserService.changeUserPassword().
   */
  submitForm() {
    this.currentPasswordWrong = false;
    this.passwordsDoNotMatch = this.newPassword !== this.newPasswordConfirm;
    this.invalidPassword = UserService.validatePassword(this.newPassword);

    if (!this.passwordsDoNotMatch && !this.invalidPassword) {
      this.userService.login(UserService.getLoggedInUser().username, this.oldPassword) // authenticate with the current password
        .then(() => this.userService.changeUserPassword(this.newPassword) // change the password
          .then(() => { // login again to refresh the token and navigate to the dashboard when done
            this.router.navigate(['/dashboard']);
            this.userService.login(UserService.getLoggedInUser().username, this.newPassword);
          })
          .catch(() => this.invalidPassword = true))
        .catch(() => this.currentPasswordWrong = true);
    }
  }
}
