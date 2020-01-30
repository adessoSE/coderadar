import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginComponent} from './login.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {RouterTestingModule} from '@angular/router/testing';
import {MainDashboardComponent} from '../main-dashboard/main-dashboard.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {AppComponent} from '../../app.component';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let http;
  let routerSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        LoginComponent,
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
          {path: 'login', component: LoginComponent},
        ]),
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should login user', () => {
    component.username = 'test';
    component.password = 'password123';
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
      expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
    });
  });

  it('should login user invalid password', () => {
    component.username = 'test';
    component.password = 'password';
    component.submitForm();
    fixture.whenStable().then(() => {
      expect(component.validPassword).toBeFalsy();
    });
  });

  it('should login user not found', () => {
    component.username = 'test';
    component.password = 'password123';
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}user/auth`).flush({
      status: 404,
      error: 'Not Found',
      message: 'Not Found',
      url: '/user/auth',
    }, {
      status: 404,
      statusText: 'Not Found',
      url: '/user/auth',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(component.invalidUser).toBeTruthy();
      });
    });
  });

  it('should login user forbidden', () => {
    component.username = 'test';
    component.password = 'password123';
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
        expect(component.invalidUser).toBeTruthy();
      });
    });
  });
});
