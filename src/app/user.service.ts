import { Injectable } from '@angular/core';
import {HttpClient , HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from './user';
import {map} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  private apiURL = 'http://localhost:8080/';

  public register(usernameValue: string, passwordValue: string) {
    const requestBody = {username: usernameValue, password: passwordValue};
    return this.httpClient.post<User>(this.apiURL + 'user/registration', JSON.stringify(requestBody), {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'});
  }

  public login(usernameValue: string, passwordValue: string) {
    const requestBody = {username: usernameValue, password: passwordValue};
    return this.httpClient.post<User>(this.apiURL + 'user/auth', JSON.stringify(requestBody), {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'});
  }
}
