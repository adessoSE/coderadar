import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {Title} from '@angular/platform-browser';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.scss']
})
export class UserSettingsComponent implements OnInit {

  oldPassword = '';
  newPassword = '';
  newPasswordConfirm = '';

  passwordsDoNotMatch = false;
  validPassword = true;
  currentPasswordWrong = false;
  passwordsAreSame = false;

  constructor(private router: Router, private userService: UserService, private titleService: Title) {
    titleService.setTitle('Coderadar - User setting');
  }

  ngOnInit(): void {
    UserService.getLoggedInUser();
  }

  /**
   * Is called upon form submission. Validates user input and calls
   * UserService.changeUserPassword().
   */
  submitForm() {
    this.currentPasswordWrong = false;
    this.passwordsAreSame = false;
    this.passwordsDoNotMatch = this.newPassword !== this.newPasswordConfirm;
    this.validPassword = UserService.validatePassword(this.newPassword);
    this.passwordsAreSame = this.oldPassword === this.newPassword;

    if (!this.passwordsDoNotMatch && this.validPassword && !this.passwordsAreSame) {
      this.userService.login(UserService.getLoggedInUser().username, this.oldPassword) // authenticate with the current password
        .then(() => this.userService.changeUserPassword(this.newPassword) // change the password
          .then(() => { // login again to refresh the token and navigate to the dashboard when done
            this.router.navigate(['/dashboard']);
            this.userService.login(UserService.getLoggedInUser().username, this.newPassword);
          })
          .catch(() => this.validPassword = true))
        .catch(() => this.currentPasswordWrong = true);
    }
  }
}
