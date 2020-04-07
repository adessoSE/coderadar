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
import {LoginComponent} from "../login/login.component";
import {By} from "@angular/platform-browser";

let registerButton;
let fixture;
let usernameInput;
let passwordInput;
let passwordConfirmInput;

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

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let routerSpy;
  let submitFormSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        FormsModule, // ngModel
        HttpClientTestingModule,
        RouterTestingModule,
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake(() => {});
    submitFormSpy = spyOn(component, 'submitForm').and.callFake(() => {});
    fixture.detectChanges();
    usernameInput = fixture.debugElement.query(By.css('input[name="username"]')).nativeElement;
    passwordInput = fixture.debugElement.query(By.css('input[name="password"]')).nativeElement;
    passwordConfirmInput = fixture.debugElement.query(By.css('input[name="confirmPassword"]')).nativeElement;
    registerButton = fixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
    return fixture.whenStable().then(() => {
      fixture.detectChanges();
    });
  });

  it('should register user in HTML', () => {
    setValues('testUser', 'testPassword', 'testPassword');
    registerButton.click();
    expect(submitFormSpy).toHaveBeenCalled();
  });

  it('should register user user already exists in HTML', () => {
    setValues('testUser', 'testPassword', 'testPassword');
    registerButton.click();
    expect(submitFormSpy).toHaveBeenCalled();
    component.invalidUser = true;
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-error')).nativeElement.innerText)
      .toBe('The chosen username is already in use!');
  });

  it('should register user passwords do not match in HTML', () => {
    setValues('testUser', 'testPassword', 'test');
    registerButton.click();
    expect(submitFormSpy).toHaveBeenCalled();
    component.passwordsDoNotMatch = true;
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-error')).nativeElement.innerText)
      .toEqual('Passwords do not match!');
  });

  it('should login user invalid password in HTML', () => {
    setValues('testUser', 'test', 'test');
    registerButton.click();
    expect(submitFormSpy).toHaveBeenCalled();
    component.validPassword = false;
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-error')).nativeElement.innerText)
      .toEqual('Password must be at least\n8 symbols long and contain a character and a digit!');
  });
});

function setValues(username, password, confirmPassword) {
  usernameInput.value = username;
  passwordInput.value = password;
  passwordConfirmInput.value = confirmPassword;

  usernameInput.dispatchEvent(new Event('input'));
  passwordInput.dispatchEvent(new Event('input'));
  passwordConfirmInput.dispatchEvent(new Event('input'));
  fixture.detectChanges();
}
