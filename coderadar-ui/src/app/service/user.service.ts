import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {AppComponent} from '../app.component';
import {User} from "../model/user";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiURL = AppComponent.getApiUrl();

  constructor(private router: Router, private httpClient: HttpClient) {
  }

  /**
   * Returns true if the given string is at least 8 symbols long
   * and contains both a digit and a character.
   * @param password the string to check.
   */
  public static validatePassword(password: string): boolean {
    if (password !== undefined) {
      return !(password.length < 8 || !/\d/g.test(password) || !/\d+/g.test(password));
    } else {
      return false;
    }
  }

  /**
   * returns the currently logged in user or null if they don't exist.
   */
  public static getLoggedInUser() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    return  currentUser === null ? '' : currentUser;
  }

  /**
   * Send a POST request to /user/registration with the username and password
   * @param usernameValue The username
   * @param passwordValue The password in plaintext
   */
  public register(usernameValue: string, passwordValue: string) {
    return this.httpClient.post(this.apiURL + 'user/registration', {username: usernameValue, password: passwordValue},
      {observe: 'response', headers: {
          'Content-Type': 'application/json'
        }}).toPromise();
  }

  /**
   * Sends a POST request to /user/auth with the given username and password.
   * Upon receiving a successful response from the server. The recieved access and
   * refresh tokens are saved to the localStorage with the key "currentUser".
   * @param usernameValue The username
   * @param passwordValue The password in plaintext
   */
  public login(usernameValue: string, passwordValue: string) {
    return this.httpClient.post<any>(this.apiURL + 'user/auth',
      {username: usernameValue, password: passwordValue}, {headers: {
          'Content-Type': 'application/json'
        }}).toPromise()
      .then(user => {
        if (user && user.accessToken && user.refreshToken) {
          user.username = usernameValue;
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
      });
  }

  /**
   * Sends a POST request to /user/refresh with the current access and refresh tokens.
   * Upon receiving a successful response from the server, the new access token is saved in the localStorage.
   * If no user is found in the local storage or the refresh token has expired, redirects to /login.
   */
  public refresh(callback: () => any) {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser && currentUser.refreshToken && currentUser.accessToken) {
      this.httpClient.post<any>(this.apiURL + 'user/refresh',
        {accessToken: currentUser.accessToken, refreshToken: currentUser.refreshToken}).toPromise()
        .then(user => {
          if (user && user.token) {
            currentUser.accessToken = user.token;
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            callback();
          }
        })
        .catch(error => {
          if (error.errorMessage !== 'Access token ist still valid. This token must be used for authentication.') {
            this.logout();
          }
        });
    } else {
      this.logout();
    }
  }

  /**
   * Deletes the access and refresh tokens from the localStorage and
   * redirects to /login
   */
  public logout() {
    localStorage.removeItem('currentUser');
    this.router.navigate(['/login']);
  }

  /**
   * Sends a POST request to /user/password/change with the refreshToken and new password.
   * @param password The new password
   */
  public changeUserPassword(password: string) {
    return this.httpClient.post(this.apiURL + 'user/password/change',
      {refreshToken: UserService.getLoggedInUser().refreshToken, newPassword: password}, {observe: 'response'}).toPromise();
  }

  public listUsersForProject(projectId: number): Promise<HttpResponse<User[]>>{
    return this.httpClient.get<User[]>(this.apiURL + 'projects/'+projectId+'/users',
  {observe: 'response'}).toPromise();
  }

  public listUsers(): Promise<HttpResponse<User[]>>{
    return this.httpClient.get<User[]>(this.apiURL + 'users',
      {observe: 'response'}).toPromise();
  }
}
