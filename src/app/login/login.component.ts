import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {UserService} from '../user.service';
import {User} from '../user';
import {FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username: string;
  public password: string;

  private accessToken = '';
  private refreshToken = '';

  invalidData = false;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  submitForm() {

    const user = this.userService.login(this.username, this.password);

    user.forEach(e => {
      console.log(e);
      if (e.body.accessToken !== undefined && e.body.refreshToken !== undefined) {
        this.router.navigate(['/dashboard']);
      } else {
        this.invalidData = true;
      }});

    this.invalidData = true;
  }
}

