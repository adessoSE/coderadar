import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public username: string;
  public password: string;
  public confirmPassword: string;

  invalidPassword = false;
  passwordsDoNotMatch = false;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  submitForm() {
    this.passwordsDoNotMatch = false;
    this.invalidPassword = false;
    if (this.password !== this.confirmPassword) {
      this.passwordsDoNotMatch = true;
    }
    if (this.password.length < 8) {
      this.invalidPassword = true;
    }  else {
      this.userService.register(this.username, this.password).toPromise().then(e =>
        this.router.navigate(['/dashboard']))
        .catch(e => {
          if (e.hasOwnProperty('error')) {
            if (e.error.errorMessage === 'Validation Error') {
              if (e.error.fieldErrors.length > 0) {
                if (e.error.fieldErrors[0].field === 'password') {
                  this.invalidPassword = true;
                }
              }
            }
          }
        });
    }
  }

}
