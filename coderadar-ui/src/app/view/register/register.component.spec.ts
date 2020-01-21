import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {RegisterComponent} from './register.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {HttpClientModule} from '@angular/common/http';
import {RouterTestingModule} from '@angular/router/testing';
import {AppComponent} from '../../app.component';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let http;
  let routerSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        RegisterComponent,
      ],
      imports: [
        FormsModule, // ngModel
        HttpClientModule,
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should register user', inject([UserService], (userService: UserService) => {
    component.username = 'test';
    component.password = 'password123';
    component.confirmPassword = 'password123';
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}user/registration`).flush({id: 1}, {
      status: 201,
      url: '/user/registration',
      statusText: 'Created',
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
        expect(UserService.getLoggedInUser().username).toBe('test');
      });
      // expect(loginSpy).toHaveBeenCalledWith('test', 'password123');
    });
  }));

  it('should register user invalid password', () => {
    component.username = 'test';
    component.password = 'password';
    component.confirmPassword = 'password';
    component.submitForm();
    expect(component.validPassword).toBeFalsy();
  });

  it('should register user passwords do not match', () => {
    component.username = 'test';
    component.password = 'password123';
    component.confirmPassword = 'password124';
    component.submitForm();
    expect(component.passwordsDoNotMatch).toBeTruthy();
  });

  it('should register user user already exists', () => {
    component.username = 'test';
    component.password = 'password123';
    component.confirmPassword = 'password123';
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}user/registration`).flush({
      status: 409,
      error: 'Conflict',
      errorMessage: 'A user with the username test already exists!',
      path: '/user/registration'
    }, {
      status: 409,
      statusText: 'Conflict',
      url: '/user/registration',
    });
    fixture.whenStable().then(() => {
      expect(component.invalidUser).toBeTruthy();
    });
  });
});
