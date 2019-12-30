import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UserSettingsComponent} from './user-settings.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {Title} from '@angular/platform-browser';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {HttpClient, HttpHandler, HttpResponse} from '@angular/common/http';
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

describe('UserSettingsComponent', () => {
  let component: UserSettingsComponent;
  let fixture: ComponentFixture<UserSettingsComponent>;
  let routerSpy;
  let userService;

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
        RouterTestingModule.withRoutes([
          {path: 'dashboard', component: MainDashboardComponent},
        ]),
      ],
      providers: [
        Title,
        HttpClient,
        HttpHandler,
        {provide: UserService, useClass: MockUserService}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(UserSettingsComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change user settings', () => {
    userService.login('test', 'password123');
    component.newPassword = 'password1234';
    component.newPasswordConfirm = 'password1234';
    component.oldPassword = 'password123';
    component.submitForm();
    fixture.whenStable().then(() => {
      expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
      expect(component.passwordsDoNotMatch).toBeFalsy();
      expect(component.passwordsAreSame).toBeFalsy();
      expect(UserService.getLoggedInUser().username).toBe('test');
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
  changeUserPassword(newPassword: string) {
    return of(new HttpResponse({
      body: {
        id: 1
      }
    })).toPromise();
  }
}
