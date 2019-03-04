import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  invalidUser = false;
  invalidPassword = false;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  submitForm() {
    this.userService.login(this.username, this.password).toPromise().then(e =>
      this.router.navigate(['/dashboard']))
      .catch(e => {
        if (e.hasOwnProperty('error')) {
          if (e.error.errorMessage === 'Validation Error') {
            if (e.error.fieldErrors.length > 0) {
              if (e.error.fieldErrors[0].field === 'password') {
                this.invalidPassword = true;
                this.invalidUser = false;
              }
            }
          } else if (e.status === 500) {
            this.invalidUser = true;
            this.invalidPassword = false;
          }
        }
      });
  }
}

