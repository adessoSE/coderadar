import {LayoutModule} from '@angular/cdk/layout';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MatButtonModule, MatCardModule, MatGridListModule, MatIconModule, MatMenuModule, } from '@angular/material';

import {MainDashboardComponent} from './main-dashboard.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Title} from '@angular/platform-browser';
import {UserService} from '../../service/user.service';
import {RouterTestingModule} from '@angular/router/testing';
import {
  ActionsSubject,
  ReducerManager,
  ReducerManagerDispatcher,
  ScannedActionsSubject,
  Store
} from '@ngrx/store';
import {AppEffects} from '../../city-map/shared/effects';
import {Actions} from '@ngrx/effects';
import {HttpClient, HttpHandler, HttpResponse} from '@angular/common/http';
import {ProjectService} from '../../service/project.service';
import {Project} from '../../model/project';
import {of} from 'rxjs';

describe('MainDashboardComponent', () => {
  let component: MainDashboardComponent;
  let fixture: ComponentFixture<MainDashboardComponent>;
  let projectService;
  const mockSnackbar = jasmine.createSpyObj(['open']);
  let userService;

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
        RouterTestingModule
      ],
      providers: [
        {provide: MatSnackBar, useValue: mockSnackbar},
        {provide: UserService, useClass: MockUserService},
        {provide: ProjectService, useClass: MockProjectService},
        Store,
        AppEffects,
        ActionsSubject,
        Actions,
        ScannedActionsSubject,
        HttpClient,
        HttpHandler,
        {provide: ReducerManager},
        ReducerManagerDispatcher,
        {provide: ActivatedRoute},
        Title
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(MainDashboardComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    projectService = TestBed.get(ProjectService);
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });

  it('should delete project', () => {
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

    // project = projectService.addProject(project).then(response => response.body);
    component.projects.push(project);
    expect(component.projects.length).toBe(1);
    component.deleteProject(project);
    fixture.whenStable().then(() => {
      expect(component.projects.length).toBe(0);
    });
  });

  it('should start analysis', () => {
    component.startAnalysis(1);
    fixture.whenStable().then(() => {
      expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis started!', '🞩', {duration: 4000});
    });
  });

  it('should reset analysis', () => {
    component.resetAnalysis(1);
    fixture.whenStable().then(() => {
      expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis results deleted!', '🞩', {duration: 4000});
    });
  });

  it('should stop analysis', () => {
    component.stopAnalysis(1);
    fixture.whenStable().then(() => {
      expect(mockSnackbar.open).toHaveBeenCalledWith('Analysis stopped!', '🞩', {duration: 4000});
    });
  });
});

class MockUserService extends UserService {
  refresh(callback: () => any) {
    callback();
  }
}

class MockProjectService extends ProjectService {
  addProject(project: Project) {
    project.id = 1;
    return of(new HttpResponse({
      body: project
    })).toPromise();
  }

  deleteProject(id: number): Promise<HttpResponse<any>> {
    return of(new HttpResponse({
      status: 200
    })).toPromise();
  }

  startAnalyzingJob(id: number, rescanProject: boolean): Promise<HttpResponse<any>> {
    return of(new HttpResponse({
      status: 200
    })).toPromise();
  }

  resetAnalysis(id: number, rescanProject: boolean): Promise<HttpResponse<any>> {
    return of(new HttpResponse({
      status: 200
    })).toPromise();
  }

  stopAnalyzingJob(id: number): Promise<HttpResponse<any>> {
    return of(new HttpResponse({
      status: 200
    })).toPromise();
  }
}
