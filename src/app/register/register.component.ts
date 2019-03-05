import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  username: string;
  password: string;
  confirmPassword: string;

  invalidUser = false;
  invalidPassword = false;
  passwordsDoNotMatch = false;

  constructor(private router: Router, private userService: UserService) { }

  submitForm() {
    this.invalidUser = false;
    this.passwordsDoNotMatch = false;
    this.invalidPassword = false;

    if (this.password !== this.confirmPassword) {
      this.passwordsDoNotMatch = true;
    }
    if (this.password.length < 8) {
      this.invalidPassword = true;
    }  else {
      this.userService.register(this.username, this.password).then(e =>
        this.router.navigate(['/login']))
        .catch(e => {
          if (e.hasOwnProperty('error')) {
            if (e.error.errorMessage === 'Validation Error') {
              if (e.error.fieldErrors.length > 0) {
                if (e.error.fieldErrors[0].field === 'password') {
                  this.invalidPassword = true;
                }
              }
            } else if (e.error.errorMessage === 'User ' + this.username + ' is already registered') {
              this.invalidUser = true;
            }
          }
        });
    }
  }
}
