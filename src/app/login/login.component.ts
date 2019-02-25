import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {UserService} from '../user.service';
import {User} from '../user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username: string;
  public password: string;

  private accessToken: string;
  private refreshToken: string;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  submitForm() {
    const user = this.userService.login(this.username, this.password);

    user.forEach(e => this.accessToken = e.body.accessToken);
    user.forEach(e => this.refreshToken = e.body.refreshToken);

    if (this.accessToken === undefined || this.refreshToken === undefined) {
      alert('Wrong username or password');
    } else {
      this.router.navigate(['/dashboard']);
    }
  }

}
