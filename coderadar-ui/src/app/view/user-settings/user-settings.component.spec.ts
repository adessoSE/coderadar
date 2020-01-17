import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {UserSettingsComponent} from './user-settings.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {Title} from '@angular/platform-browser';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {HttpClient, HttpClientModule, HttpHandler, HttpResponse} from '@angular/common/http';
import {MainDashboardComponent} from '../main-dashboard/main-dashboard.component';
import {RouterTestingModule} from '@angular/router/testing';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {AppComponent} from "../../app.component";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('UserSettingsComponent', () => {
  let component: UserSettingsComponent;
  let fixture: ComponentFixture<UserSettingsComponent>;
  let routerSpy;
  let http;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        UserSettingsComponent,
        MainDashboardComponent
      ],
      imports: [
        FormsModule, // ngModel
        NoopAnimationsModule,
        LayoutModule,
        MatButtonModule,
        MatCardModule,
        MatGridListModule,
        MatSnackBarModule,
        MatIconModule,
        MatMenuModule,
        HttpClientModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([
          {path: 'dashboard', component: MainDashboardComponent},
        ]),
      ],
      providers: [
        // Title,
        // HttpClient,
        // HttpHandler,
        // {provide: UserService, useClass: MockUserService}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(UserSettingsComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change user settings', () => {
    component.newPassword = 'password1234';
    component.newPasswordConfirm = 'password1234';
    component.oldPassword = 'password123';
    const user = {
      username: 'test',
      accessToken: 'accessToken',
      refreshToken: 'refreshToken'
    };
    localStorage.setItem('currentUser', JSON.stringify(user));
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}user/auth`).flush({
      accessToken: 'test',
      refreshToken: 'test'
    }, {
      status: 200,
      statusText: 'Ok',
      url: '/user/auth',
    });
    fixture.whenStable().then(() => {
      http.expectOne(`${AppComponent.getApiUrl()}user/password/change`).flush({}, {
        status: 200,
        statusText: 'Ok',
        url: '/user/password/change',
      });
      fixture.whenStable().then(() => {
        http.expectOne(`${AppComponent.getApiUrl()}user/auth`).flush({
          accessToken: 'test',
          refreshToken: 'test'
        }, {
          status: 200,
          statusText: 'Ok',
          url: '/user/auth'
        });
        fixture.whenStable().then(() => {
          expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
          expect(component.passwordsDoNotMatch).toBeFalsy();
          expect(component.passwordsAreSame).toBeFalsy();
          expect(UserService.getLoggedInUser().username).toBe('test');
        });
      });
    });
  });

  it('should change user settings invalid password', () => {
    component.newPassword = 'password';
    component.newPasswordConfirm = 'password';
    component.oldPassword = 'password123';
    component.submitForm();
    expect(component.validPassword).toBeFalsy();
  });

  it('should change user settings passwords do not match', () => {
    component.newPassword = 'password1234';
    component.newPasswordConfirm = 'password234';
    component.oldPassword = 'password123';
    component.submitForm();
    expect(component.passwordsDoNotMatch).toBeTruthy();
  });

  it('should change user settings passwords are the same', () => {
    component.newPassword = 'password123';
    component.newPasswordConfirm = 'password123';
    component.oldPassword = 'password123';
    component.submitForm();
    expect(component.passwordsAreSame).toBeTruthy();
  });

  it('should change user settings unauthenticated', () => {
    component.newPassword = 'password1234';
    component.newPasswordConfirm = 'password1234';
    component.oldPassword = 'password123';
    const user = {
      username: 'test',
      accessToken: 'accessToken',
      refreshToken: 'refreshToken'
    };
    localStorage.setItem('currentUser', JSON.stringify(user));
    component.submitForm();
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
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(component.currentPasswordWrong).toBeTruthy();
      });
    });
  });
});
