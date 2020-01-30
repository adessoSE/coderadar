import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {ViewCommitComponent} from './view-commit.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from "../../app.component";
import {Project} from "../../model/project";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {MainDashboardComponent} from "../main-dashboard/main-dashboard.component";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {LayoutModule} from "@angular/cdk/layout";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";

describe('ViewCommitComponent', () => {
  let component: ViewCommitComponent;
  let fixture: ComponentFixture<ViewCommitComponent>;
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
        ViewCommitComponent,
        MainDashboardComponent
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      imports: [
        NoopAnimationsModule,
        LayoutModule,
        MatButtonModule,
        MatCardModule,
        MatGridListModule,
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
            params: of({id: 1, name: 'test'})
          }
        }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ViewCommitComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get commit info', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/metrics`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/metrics',
    });
    fixture.whenStable().then(() =>  {
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/metricvalues/perCommit`).flush([], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/metricValues/perCommit',
      });
      fixture.whenStable().then(() => {
        component.projectId = 1;
        component.commit.name = 'initial commit';
        (component as any).getCommitInfo();
        http.expectOne(`${AppComponent.getApiUrl()}projects/1/metrics`).flush(['metric1', 'metric2'], {
          status: 200,
          statusText: 'Ok',
          url: '/projects/1/metrics',
        });
        fixture.whenStable().then(() =>  {
          http.expectOne(`${AppComponent.getApiUrl()}projects/1/metricvalues/perCommit`).flush([{
            metricName: 'metric1',
            value: '20'
          }, {
            metricName: 'metric2',
            value: '15'
          }], {
            status: 200,
            statusText: 'Ok',
            url: '/projects/1/metricValues/perCommit',
          });
          fixture.whenStable().then(() => {
            expect(component.metrics.length).toBe(2);
            expect(component.metrics[0].value).toBe('20');
          });
        });
      });
    });
  });

  it('should get commit info forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/metrics`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/metrics',
    });
    fixture.whenStable().then(() =>  {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {});
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/metricvalues/perCommit`).flush([], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/metricValues/perCommit',
      });
      fixture.whenStable().then(() => {
        component.projectId = 1;
        component.commit.name = 'initial commit';
        (component as any).getCommitInfo();
        http.expectOne(`${AppComponent.getApiUrl()}projects/1/metrics`).flush(['metric1', 'metric2'], {
          status: 200,
          statusText: 'Ok',
          url: '/projects/1/metrics',
        });
        fixture.whenStable().then(() =>  {
          http.expectOne(`${AppComponent.getApiUrl()}projects/1/metricvalues/perCommit`).flush({
            status: 403,
            error: 'Forbidden',
            message: 'Access Denied',
            path: '/projects/1/metricValues/perCommit'
          }, {
            status: 403,
            statusText: 'Forbidden',
            url: '/projects/1/metricValues/perCommit',
          });
          fixture.whenStable().then(() => {
            fixture.whenStable().then(() => {
              expect(refreshSpy).toHaveBeenCalled()
            });
          });
        });
      });
    });
  }));

  it('should get commit info get commit metrics value forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/metrics`).flush(['metric1', 'metric2'], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/metrics',
    });
    fixture.whenStable().then(() =>  {
      (component as any).getCommitInfo();
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {});
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/metrics`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects/1/metrics'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects/1/metrics',
      });
      fixture.whenStable().then(() =>  {
        expect(refreshSpy).toHaveBeenCalled()
      });
    });
  }));

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
  }));
});
