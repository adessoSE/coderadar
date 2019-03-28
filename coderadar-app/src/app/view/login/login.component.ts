import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {INTERNAL_SERVER_ERROR} from 'http-status-codes';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  username: string;
  password: string;

  invalidUser = false;
  invalidPassword = false;

  constructor(private router: Router, private userService: UserService) { }

  /**
   * Called when the form is submitted, upon successful login, redirects
   * to the dashboard.
   */
  submitForm(): void {
    this.invalidUser = false;
    this.invalidPassword = UserService.validatePassword(this.password);
    if (!this.invalidPassword) {
      this.userService.login(this.username, this.password)
        .then(e => {
          this.router.navigate(['/dashboard']);
        })
        .catch(e => {
          if (e.status === INTERNAL_SERVER_ERROR) {
            this.invalidUser = true;
          }
        });
    }
  }
}

