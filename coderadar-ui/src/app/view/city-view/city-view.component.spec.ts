import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {CityViewComponent} from './city-view.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {AppEffects} from '../../city-map/shared/effects';
import {UserService} from '../../service/user.service';
import {Title} from '@angular/platform-browser';
import {of} from 'rxjs';
import {AppComponent} from "../../app.component";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {MainDashboardComponent} from "../main-dashboard/main-dashboard.component";
import {FormsModule} from "@angular/forms";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {LayoutModule} from "@angular/cdk/layout";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from "@angular/material/menu";

describe('CityViewComponent', () => {
  let component: CityViewComponent;
  let fixture: ComponentFixture<CityViewComponent>;
  let http;
  let routerSpy;
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
        CityViewComponent,
        MainDashboardComponent
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
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
        {provide: ActivatedRoute, useValue: {
            params: of({ id: 1 })
          }
        },
        {provide: AppEffects, useValue: {currentProjectId: 1}},
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CityViewComponent);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set title', inject([Title], (titleService: Title) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
      status: 200,
      url: '/projects/1',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      const titleSpy = spyOn(titleService, 'setTitle').and.callThrough();
      (component as any).setTitle(project.id);
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
        status: 200,
        url: '/projects/1',
        statusText: 'Ok',
      });
      fixture.whenStable().then(() => {
        expect(titleSpy).toHaveBeenCalledWith('Coderadar - ' + AppComponent.trimProjectName(project.name) + ' - 3D view');
      });
    });
  }));

  it('should set title forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
      status: 200,
      url: '/projects/1',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      (component as any).setTitle(project.id);
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

  it('should set title not found', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
      status: 200,
      url: '/projects/1',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      (component as any).setTitle(project.id);
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({
        status: 404,
        error: 'Not Found',
        message: 'Not Found',
        url: '/projects/1',
      }, {
        status: 404,
        statusText: 'Not Found',
        url: '/projects/1',
      });
      fixture.whenStable().then(() => {
        expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
      });
    });
  });
});
