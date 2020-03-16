import {TestBed} from '@angular/core/testing';

import {UserService} from './user.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {Router} from '@angular/router';
import {AppComponent} from "../app.component";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {LoginComponent} from "../view/login/login.component";
import {MainDashboardComponent} from "../view/main-dashboard/main-dashboard.component";
import createSpy = jasmine.createSpy;
import {FormsModule} from "@angular/forms";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {LayoutModule} from "@angular/cdk/layout";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from "@angular/material/menu";
import {MatFormFieldModule} from "@angular/material/form-field";

describe('UserService', () => {
  let service: UserService;
  let http;
  let logoutSpy;
  let routerSpy;
  const user = {
    username: 'test',
    accessToken: 'accessToken',
    refreshToken: 'refreshToken'
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        LoginComponent
      ],
      imports: [
        FormsModule, // ngModel
        NoopAnimationsModule,
        LayoutModule,
        MatButtonModule,
        MatCardModule,
        MatGridListModule,
        MatSnackBarModule,
        MatFormFieldModule,
        MatIconModule,
        MatMenuModule,
        HttpClientTestingModule,
        HttpClientModule,
        RouterTestingModule.withRoutes([
          {path: 'login', component: LoginComponent},
        ])
      ],
      /*providers: [
        {provide: Router}
      ]*/
    });
    http = TestBed.get(HttpTestingController);
    service = TestBed.get(UserService);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {
    });
    logoutSpy = spyOn(service, 'logout').and.callThrough();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should validate password', () => {
    expect(UserService.validatePassword('password123')).toBeTruthy();
    expect(UserService.validatePassword('password')).toBeFalsy();
    expect(UserService.validatePassword('pass123')).toBeFalsy();
    // TODO should fail
    // expect(UserService.validatePassword('12345678')).toBeFalsy();
  });

  it('should get logged in user', () => {
    localStorage.setItem('currentUser', JSON.stringify(user));
    expect(UserService.getLoggedInUser().username).toBe('test');
  });

  it('should get logged in user', () => {
    expect(UserService.getLoggedInUser().username).toBe('test');
  });

  it('should login', () => {
    service.login('test', 'password123');
    http.expectOne(`${AppComponent.getApiUrl()}user/auth`).flush({
      accessToken: 'accessToken',
      refreshToken: 'refreshToken'
    }, {
      status: 200,
      url: '/user/auth',
      statusText: 'Ok',
    });
    expect(JSON.parse(localStorage.getItem('currentUser')).username).toBe('test');
    expect(JSON.parse(localStorage.getItem('currentUser')).accessToken).toBe('accessToken');
    expect(JSON.parse(localStorage.getItem('currentUser')).refreshToken).toBe('refreshToken');
    localStorage.removeItem('currentUser');
  });

  // TODO response on login fail?
  /*it('should login fail', () => {
    service.login('test', 'password123');
    http.expectOne(`${AppComponent.getApiUrl()}user/auth`).flush({
      status: 403,
      error: 'Forbidden',
      message: 'Access Denied',
      path: '/user/auth'
    }, {
      status: 403,
      statusText: 'Forbidden',
      url: '/user/auth',
    });
    expect(localStorage.getItem('currentUser')).toBe(undefined);
  });*/

  it('should refresh', () => {
    localStorage.setItem('currentUser', JSON.stringify(user));
    service.refresh(() => {
      expect(JSON.parse(localStorage.getItem('currentUser')).accessToken).toBe('newToken');
    });
    http.expectOne(`${AppComponent.getApiUrl()}user/refresh`).flush({
      token: 'newToken',
    }, {
      status: 200,
      url: '/user/refresh',
      statusText: 'Ok',
    });
  });

  it('should refresh access token still valid', () => {
    localStorage.setItem('currentUser', JSON.stringify(user));
    service.refresh(() => {
      expect(JSON.parse(localStorage.getItem('currentUser')).username).toBe('test');
      expect(JSON.parse(localStorage.getItem('currentUser')).accessToken).toBe('accessToken');
      expect(JSON.parse(localStorage.getItem('currentUser')).refreshToken).toBe('refreshToken');
    });
    http.expectOne(`${AppComponent.getApiUrl()}user/refresh`).flush({
      status: 400,
      error: 'Bad Request',
      message: 'Access token ist still valid. This token must be used for authentication.',
      path: '/user/refresh'
    }, {
      status: 400,
      url: '/user/refresh',
      statusText: 'Bad Request',
    });
  });

  it('should refresh user with username not found', () => {
    localStorage.setItem('currentUser', JSON.stringify(user));
    service.refresh(() => {
      expect(logoutSpy).toHaveBeenCalled();
      expect(localStorage.getItem('currentUser')).toBeNull();
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
    });
    http.expectOne(`${AppComponent.getApiUrl()}user/refresh`).flush({
      status: 400,
      error: 'Bad Request',
      message: 'User with username test not found.',
      path: '/user/refresh'
    }, {
      status: 400,
      url: '/user/refresh',
      statusText: 'Bad Request',
    });
  });

  it('should refresh user with id not found', () => {
    localStorage.setItem('currentUser', JSON.stringify(user));
    service.refresh(() => {
      expect(logoutSpy).toHaveBeenCalled();
      expect(localStorage.getItem('currentUser')).toBeNull();
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
    });
    http.expectOne(`${AppComponent.getApiUrl()}user/refresh`).flush({
      status: 400,
      error: 'Bad Request',
      message: 'User with id 1 not found.',
      path: '/user/refresh'
    }, {
      status: 400,
      url: '/user/refresh',
      statusText: 'Bad Request',
    });
  });

  it('should refresh refresh token not found', () => {
    localStorage.setItem('currentUser', JSON.stringify(user));
    service.refresh(() => {
      expect(logoutSpy).toHaveBeenCalled();
      expect(localStorage.getItem('currentUser')).toBeNull();
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
    });
    http.expectOne(`${AppComponent.getApiUrl()}user/refresh`).flush({
      status: 400,
      error: 'Bad Request',
      message: 'User with id 1 not found.',
      path: '/user/refresh'
    }, {
      status: 400,
      url: '/user/refresh',
      statusText: 'Bad Request',
    });
  });

  it('should refresh no accessToken', () => {
    const noAccessTokenUser = {
      username: 'test',
      refreshToken: 'refreshToken'
    };
    localStorage.setItem('currentUser', JSON.stringify(noAccessTokenUser));
    service.refresh(() => {
      expect(logoutSpy).toHaveBeenCalled();
      expect(localStorage.getItem('currentUser')).toBeNull();
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
    });
  });

  it('should refresh no refreshToken', () => {
    const noRefreshTokenUser = {
      username: 'test',
      accessToken: 'accessToken'
    };
    localStorage.setItem('currentUser', JSON.stringify(noRefreshTokenUser));
    service.refresh(() => {
      expect(logoutSpy).toHaveBeenCalled();
      expect(localStorage.getItem('currentUser')).toBeNull();
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
    });
  });

  it('should refresh no username', () => {
    const noUsernameTokenUser = {
      accessToken: 'accessToken',
      refreshToken: 'refreshToken'
    };
    localStorage.setItem('currentUser', JSON.stringify(noUsernameTokenUser));
    service.refresh(() => {
      expect(logoutSpy).toHaveBeenCalled();
      expect(localStorage.getItem('currentUser')).toBeNull();
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
    });
  });

  it('should refresh no user', () => {
    service.refresh(() => {
      expect(logoutSpy).toHaveBeenCalled();
      expect(localStorage.getItem('currentUser')).toBeNull();
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
    });
  });
});
