import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {Title} from '@angular/platform-browser';
import {CONFLICT} from 'http-status-codes';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  username: string;
  password: string;
  confirmPassword: string;

  invalidUser = false;
  validPassword = true;
  passwordsDoNotMatch = false;

  constructor(private router: Router, private userService: UserService, private titleService: Title) {
    titleService.setTitle('Coderadar - Sign up');
  }

  /**
   * Is called upon registration form submit.
   * Validates users input and sends the appropriate requests to the server
   * using the UserService.
   */
  submitForm(): void {
    this.invalidUser = false;
    this.passwordsDoNotMatch = this.password !== this.confirmPassword;
    this.validPassword = UserService.validatePassword(this.password);

    if (this.validPassword && !this.passwordsDoNotMatch) {
      this.userService.register(this.username, this.password)
        .then(() => {
          this.userService.login(this.username, this.password)
            .then(() => {
              this.router.navigate(['/dashboard']);
            });
        })
        .catch(e => {
          if (e.status === CONFLICT && e.error &&
            e.error.errorMessage === 'A user with the username ' + this.username + ' already exists!') {
            this.invalidUser = true;
          }
        });
    }
  }
}
