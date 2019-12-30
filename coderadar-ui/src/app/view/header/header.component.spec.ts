import {ComponentFixture, TestBed} from '@angular/core/testing';

import {HeaderComponent} from './header.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {MatMenuModule} from '@angular/material/menu';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {MainDashboardComponent} from '../main-dashboard/main-dashboard.component';
import {Router} from '@angular/router';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {LoginComponent} from '../login/login.component';
import {FormsModule} from '@angular/forms';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let userService;
  let routerSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        MainDashboardComponent,
        HeaderComponent,
        LoginComponent
      ],
      imports: [
        MatMenuModule, // matMenuTriggerFor
        NoopAnimationsModule,
        LayoutModule,
        MatButtonModule,
        MatCardModule,
        MatGridListModule,
        MatSnackBarModule,
        MatIconModule,
        MatMenuModule,
        FormsModule,
        RouterTestingModule.withRoutes([
          {path: 'login', component: LoginComponent},
        ]),
      ],
      providers: [
        HttpClient,
        HttpHandler,
        {provide: UserService, useClass: MockUserService},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get logged in user without user', () => {
    expect(component.getUsername()).toBe('');
  });

  it('should get logged in user', () => {
    userService.login('test', 'test');
    expect(component.getUsername()).toBe('test');
  });

  it('should log out user', () => {
    userService.login('test', 'test');
    expect(component.getUsername()).toBe('test');
    component.logout();
    fixture.whenStable().then(() => {
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
      expect(component.getUsername()).toBe('');
      expect(UserService.getLoggedInUser()).toBeFalsy();
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
