import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegisterComponent} from './register.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {HttpClient, HttpHandler, HttpResponse} from '@angular/common/http';
import {MainDashboardComponent} from '../main-dashboard/main-dashboard.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {RouterTestingModule} from '@angular/router/testing';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let userService;
  let routerSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        RegisterComponent,
        MainDashboardComponent,
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
          {path: 'dashboard', component: MainDashboardComponent},
        ]),
      ],
      providers: [
        {provide: UserService, useClass: MockUserService},
        HttpClient,
        HttpHandler
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should register user', () => {
    component.username = 'test';
    component.password = 'password123';
    component.confirmPassword = 'password123';
    component.submitForm();
    fixture.whenStable().then(() => {
      expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
    });
  });
});

class MockUserService extends UserService {
  register(usernameValue: string, passwordValue: string) {
    return of(new HttpResponse({
      body: {
        id: 1
      }
    })).toPromise();
  }
  login(usernameValue: string, passwordValue: string) {
    const user = {
      username: usernameValue,
      accessToken: 'accessToken',
      refreshToken: 'refreshToken'
    };
    return of(localStorage.setItem('currentUser', JSON.stringify(user))).toPromise();
  }
}
