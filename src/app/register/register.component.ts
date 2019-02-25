import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {UserService} from '../user.service';
import {catchError} from 'rxjs/operators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public username: string;
  public password: string;
  public confirmPassword: string;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  submitForm() {
    if (this.password !== this.confirmPassword) {
      alert('Passwords do not match');
    } else {
        const response = this.userService.register(this.username, this.password)
          .subscribe(data => this.router.navigate(['/dashboard']), error1 => alert('Invalid Username or Password values'));
    }
  }

}
