import {LayoutModule} from '@angular/cdk/layout';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {ComponentFixture, inject, TestBed} from '@angular/core/testing';
import {
  MatButtonModule,
  MatCardModule,
  MatGridListModule,
  MatIconModule,
  MatMenuModule,
} from '@angular/material';

import {MainDashboardComponent} from './main-dashboard.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserService} from '../../service/user.service';
import {RouterTestingModule} from '@angular/router/testing';
import {AppComponent} from '../../app.component';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {HttpClientModule} from '@angular/common/http';
import {Project} from "../../model/project";

describe('MainDashboardComponent', () => {
  let component: MainDashboardComponent;
  let fixture: ComponentFixture<MainDashboardComponent>;
  const mockSnackbar = jasmine.createSpyObj(['open']);
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
      declarations: [MainDashboardComponent],
      imports: [
        NoopAnimationsModule,
        LayoutModule,
        MatButtonModule,
        MatCardModule,
        MatGridListModule,
        MatIconModule,
        MatMenuModule,
        RouterTestingModule,
        HttpClientModule,
        HttpClientTestingModule,
      ],
      providers: [
        {provide: MatSnackBar, useValue: mockSnackbar},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(MainDashboardComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });

  it('should delete project', () => {
    component.projects.push(project);
    expect(component.projects.length).toBe(1);
    component.deleteProject(project);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(component.projects.length).toBe(0);
      });
    });
  });

  it('should delete project forbidden', inject([UserService], (userService: UserService) => {
    component.projects.push(project);
    expect(component.projects.length).toBe(1);
    const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
    });
    component.deleteProject(project);
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
      fixture.whenStable().then(() => {
        expect(refreshSpy).toHaveBeenCalled();
        expect(component.projects.length).toBe(1);
      });
    });
  }));

  it('should delete project unprocessable entity', () => {
    component.projects.push(project);
    expect(component.projects.length).toBe(1);
    component.deleteProject(project);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      errorMessage: 'The project test already exists.',
      path: '/projects/1'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(component.projects.length).toBe(1);
        expect(mockSnackbar.open).toHaveBeenCalledWith('Cannot delete project! Try again later!', '🞩', {duration: 4000});
      });
    });
  });

  it('should start analysis', () => {
    component.startAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyze`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/analyze',
    });
    fixture.whenStable().then(() => {
      expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis started!', '🞩', {duration: 4000});
    });
  });

  it('should start analysis forbidden', inject([UserService], (userService: UserService) => {
    const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
    });
    component.startAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyze`).flush({
      status: 403,
      error: 'Forbidden',
      errorMessage: 'Access Denied',
      path: '/projects/1/analyze'
    }, {
      status: 403,
      statusText: 'Forbidden',
      url: '/projects/1/analyze',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(refreshSpy).toHaveBeenCalled();
      });
    });
  }));

  it('should start analysis unprocessable entity no analyzers', () => {
    component.startAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyze`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      errorMessage: 'Cannot analyze project without analyzers',
      path: '/projects/1/analyze'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects/1/analyze',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(mockSnackbar.open).toHaveBeenCalledWith('Cannot analyze, no analyzers configured for this project!', '🞩', {duration: 4000});
      });
    });
  });

  it('should start analysis unprocessable entity no file patterns', () => {
    component.startAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyze`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      errorMessage: 'Cannot analyze project without file patterns',
      path: '/projects/1/analyze'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects/1/analyze',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(mockSnackbar.open).toHaveBeenCalledWith('Cannot analyze, no file patterns configured for this project!', '🞩', {duration: 4000});
      });
    });
  });

  it('should start analysis unprocessable entity other', () => {
    component.startAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyze`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      errorMessage: 'Other',
      path: '/projects/1/analyze'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects/1/analyze',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis cannot be started! Try again later!', '🞩', {duration: 4000});
      });
    });
  });

  it('should reset analysis', () => {
    component.resetAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyze/reset`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/analyze/reset',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis results deleted!', '🞩', {duration: 4000});
      });
    });
  });

  it('should reset analysis forbidden', inject([UserService], (userService: UserService) => {
    const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
    });
    component.resetAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyze/reset`).flush({
      status: 403,
      error: 'Forbidden',
      errorMessage: 'Access denied',
      path: '/projects/1/analyze/reset'
    }, {
      status: 403,
      statusText: 'Forbidden',
      url: '/projects/1/analyze/reset',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(refreshSpy).toHaveBeenCalled();
      });
    });
  }));

  it('should reset analysis unprocessable entity', () => {
    component.resetAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyze/reset`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      errorMessage: 'Cannot reset analysis',
      path: '/projects/1/analyze/reset'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects/1/analyze/reset',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis results cannot be deleted! Try again later!', '🞩', {duration: 4000});
      });
    });
  });

  it('should stop analysis', () => {
    component.stopAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/stopAnalysis`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/stopAnalysis',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis stopped!', '🞩', {duration: 4000});
      });
    });
  });

  it('should stop analysis forbidden', inject([UserService], (userService: UserService) => {
    const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
    });
    component.stopAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/stopAnalysis`).flush({
      status: 403,
      error: 'Forbidden',
      errorMessage: 'Access denied',
      path: '/projects/1/stopAnalysis'
    }, {
      status: 403,
      statusText: 'Forbidden',
      url: '/projects/1/stopAnalysis',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(refreshSpy).toHaveBeenCalled();
      });
    });
  }));

  it('should stop analysis unprocessable entity', () => {
    component.stopAnalysis(1);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/stopAnalysis`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      errorMessage: 'Cannot stop analysis',
      path: '/projects/1/stopAnalysis'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects/1/stopAnalysis',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        // TODO correct behaviour?
        expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis stopped!', '🞩', {duration: 4000});
      });
    });
  });

  it('should get projects', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects',
    });
    fixture.whenStable().then(() => {
      const project2 = {
        id: 2,
        name: 'test 2',
        vcsUrl: 'https://valid.url',
        vcsUsername: '',
        vcsPassword: '',
        vcsOnline: true,
        startDate: null,
        endDate: null
      };
      const projects = [project, project2];
      (component as any).getProjects();
      http.expectOne(`${AppComponent.getApiUrl()}projects`).flush(projects, {
        status: 200,
        url: '/projects',
        statusText: 'Ok',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.projects.length).toBe(2);
          expect(JSON.stringify(component.projects[0])).toBe(JSON.stringify(new Project(project)));
          expect(component.projects[0].name).toBe('test');
          expect(component.projects[1].name).toBe('test 2');
        });
      });
    });
  });

  it('should get project forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects',
    });
    fixture.whenStable().then(() => {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {});
      (component as any).getProjects();
      http.expectOne(`${AppComponent.getApiUrl()}projects`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(refreshSpy).toHaveBeenCalled();
        });
      });
    });
  }));
});
