import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
import {Router} from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private router: Router, private httpClient: HttpClient) { }

  private apiURL = 'http://localhost:8080/';

  public register(usernameValue: string, passwordValue: string) {
    return this.httpClient.post<User>(this.apiURL + 'user/registration', {username: usernameValue, password: passwordValue},
      {headers: new HttpHeaders().set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public login(usernameValue: string, passwordValue: string) {
    return this.httpClient.post<any>(this.apiURL + 'user/auth',
      { username: usernameValue, password: passwordValue }).toPromise().then(user => {
        if (user && user.accessToken && user.refreshToken) {
          console.log(JSON.stringify(user));
          user.username = usernameValue;
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
      });
  }


  // Refreshes the access token for the logged in user.
  // If no user is found in the local storage or the refresh token has expired->logout.
  public refresh() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    console.log(currentUser);
    if (currentUser && currentUser.refreshToken && currentUser.accessToken) {
      return this.httpClient.post<any>(this.apiURL + 'user/refresh',
        { accessToken: currentUser.accessToken, refreshToken: currentUser.refreshToken }).toPromise().then(user => {
        if (user && user.token) {
          currentUser.accessToken = user.token;
          localStorage.setItem('currentUser', JSON.stringify(currentUser));
        }
      }).catch(response => {
        this.logout();
      });
    } else {
      this.logout();
    }
  }

  public logout() {
    localStorage.removeItem('currentUser');
    this.router.navigate(['/login']);
  }

  public getLoggedInUser() {
    return JSON.parse(localStorage.getItem('currentUser'));
  }
}
