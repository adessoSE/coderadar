import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';

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
  invalidPassword = false;
  passwordsDoNotMatch = false;

  constructor(private router: Router, private userService: UserService) { }

  /**
   * Is called upon registration form submit.
   * Validates users input and sends the appropriate requests to the server
   * using the UserService.
   */
  submitForm(): void {
    this.invalidUser = false;
    this.passwordsDoNotMatch = this.password !== this.confirmPassword;
    this.invalidPassword = UserService.validatePassword(this.password);

    if (!this.invalidPassword && !this.passwordsDoNotMatch) {
      this.userService.register(this.username, this.password)
        .then(e => {
        this.userService.login(this.username, this.password)
          .then(
            () => this.router.navigate(['/dashboard']));
        })
        .catch(e => {
          if (e.error && e.error.errorMessage === 'User ' + this.username + ' is already registered') {
            this.invalidUser = true;
          }
        });
    }
  }
}
