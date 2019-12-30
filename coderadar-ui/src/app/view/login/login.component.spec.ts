import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginComponent} from './login.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {Title} from '@angular/platform-browser';
import {Router} from '@angular/router';
import {of} from 'rxjs';
import {HttpClient, HttpHandler} from '@angular/common/http';
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

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let userService;
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
        RouterTestingModule.withRoutes([
          {path: 'login', component: LoginComponent},
        ]),
      ],
      providers: [
        {provide: UserService, useClass: MockUserService},
        HttpClient,
        HttpHandler,
        Title
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
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
    fixture.whenStable().then(() => {
      expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
    });
  });
});

class MockUserService extends UserService {
  login(usernameValue: string, passwordValue: string) {
    const user = {
      username: usernameValue,
      accessToken: 'accessToken',
      refreshToken: 'refreshToken'
    };
    return of(localStorage.setItem('currentUser', JSON.stringify(user))).toPromise();
  }
}
