import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {AppComponent} from '../app.component';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private router: Router, private httpClient: HttpClient) { }

  private apiURL = AppComponent.getApiUrl();

  /**
   * Returns true if the given string is at least 8 symbols long
   * and contains both a digit and a character.
   * @param password the string to check.
   */
  public static validatePassword(password: string): boolean {
    return password.length < 8 || !/\d/g.test(password) || !/\d+/g.test(password);
  }

  /**
   * returns the currently logged in user or null if they don't exist.
   */
  public static getLoggedInUser() {
    return JSON.parse(localStorage.getItem('currentUser'));
  }

  /**
   * Send a POST request to /user/registration with the username and password
   * @param usernameValue The username
   * @param passwordValue The password in plaintext
   */
  public register(usernameValue: string, passwordValue: string) {
    return this.httpClient.post(this.apiURL + 'user/registration', {username: usernameValue, password: passwordValue},
      {observe: 'response'}).toPromise();
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
      { username: usernameValue, password: passwordValue }).toPromise()
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
  public refresh() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser && currentUser.refreshToken && currentUser.accessToken) {
      return this.httpClient.post<any>(this.apiURL + 'user/refresh',
        { accessToken: currentUser.accessToken, refreshToken: currentUser.refreshToken }).toPromise()
        .then(user => {
        if (user && user.token) {
          currentUser.accessToken = user.token;
          localStorage.setItem('currentUser', JSON.stringify(currentUser));
        }})
        .catch(() => this.logout());
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
}
