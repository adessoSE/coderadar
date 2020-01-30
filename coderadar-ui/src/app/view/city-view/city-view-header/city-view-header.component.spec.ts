import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {CityViewHeaderComponent} from './city-view-header.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {MatMenuModule} from '@angular/material/menu';
import {UserService} from '../../../service/user.service';
import {AppEffects} from '../../../city-map/shared/effects';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientModule} from '@angular/common/http';
import {Router} from '@angular/router';
import {MainDashboardComponent} from '../../main-dashboard/main-dashboard.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {LoginComponent} from '../../login/login.component';
import {FormsModule} from '@angular/forms';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AppComponent} from '../../../app.component';
import {Project} from '../../../model/project';

describe('CityViewHeaderComponent', () => {
  let component: CityViewHeaderComponent;
  let fixture: ComponentFixture<CityViewHeaderComponent>;
  let routerSpy;
  let http;
  const project = {
    id: 1,
    name: 'test',
    vcsUrl: 'https://valid.url',
    vcsUsername: '',
    vcsPassword: '',
    vcsOnline: true,
    startDate: null,
    endDate: null
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        CityViewHeaderComponent,
        MainDashboardComponent,
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
        HttpClientModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([
          {path: 'login', component: LoginComponent},
          {path: 'dashboard', component: MainDashboardComponent},
        ]),
      ],
      providers: [
        {provide: AppEffects, useValue: {
            currentProjectId: 1
          }},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CityViewHeaderComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    localStorage.clear();
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
    const user = {
      username: 'test',
      accessToken: 'accessToken',
      refreshToken: 'refreshToken'
    };
    localStorage.setItem('currentUser', JSON.stringify(user));
    expect(component.getUsername()).toBe('test');
  });

  it('should log out user', () => {
    const user = {
      username: 'test',
      accessToken: 'accessToken',
      refreshToken: 'refreshToken'
    };
    localStorage.setItem('currentUser', JSON.stringify(user));
    expect(component.getUsername()).toBe('test');
    component.logout();
    fixture.whenStable().then(() => {
      expect(routerSpy).toHaveBeenCalledWith(['/login']);
      expect(component.getUsername()).toBe('');
      expect(UserService.getLoggedInUser()).toBeFalsy();
    });
  });

  it('should get project', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
      status: 200,
      url: '/projects/1',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      (component as any).getProject();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
        status: 200,
        url: '/projects/1',
        statusText: 'Ok',
      });
      fixture.whenStable().then(() => {
        expect(JSON.stringify(component.project)).toBe(JSON.stringify(new Project(project)));
      });
    });
  });

  it('should get project forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
      status: 200,
      url: '/projects/1',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {});
      (component as any).getProject();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects/1'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects/1',
      });
      fixture.whenStable().then(() => {
        expect(refreshSpy).toHaveBeenCalled();
      });
    });
  }));

  it('should get project not found', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
      status: 200,
      url: '/projects/1',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      (component as any).getProject();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({
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
        expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
      });
    });
  }));
});
