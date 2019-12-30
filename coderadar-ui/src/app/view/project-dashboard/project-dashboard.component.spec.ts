import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProjectDashboardComponent} from './project-dashboard.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {
  ActionsSubject,
  ReducerManager, ReducerManagerDispatcher,
  ScannedActionsSubject,
  StateObservable,
  Store
} from '@ngrx/store';
import {AppEffects} from '../../city-map/shared/effects';
import {of} from 'rxjs';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {Actions} from '@ngrx/effects';
import {CityViewComponent} from '../city-view/city-view.component';

describe('ProjectDashboardComponent', () => {
  let component: ProjectDashboardComponent;
  let fixture: ComponentFixture<ProjectDashboardComponent>;
  let routerSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        ProjectDashboardComponent,
        CityViewComponent
      ],
      imports: [
        RouterTestingModule.withRoutes([
          {path: 'city/:id', component: CityViewComponent},
        ]),
      ],
      providers: [
        {
          provide: ActivatedRoute, useValue: {
            params: of({id: 1})
          }
        },
        {provide: StateObservable},
        {provide: UserService, useClass: MockUserService},
        Actions,
        ActionsSubject,
        AppEffects,
        HttpClient,
        HttpHandler,
        {provide: ReducerManager},
        ReducerManagerDispatcher,
        ScannedActionsSubject,
        Store
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ProjectDashboardComponent);
    component = fixture.componentInstance;
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    component.commits = [
      {
        name: 'init',
        author: 'testUser',
        comment: 'initial commit',
        timestamp: 1576832400000,
        analyzed: false
      },
      {
        name: 'second',
        author: 'testUser',
        comment: 'scond commit',
        timestamp: 1576832800000,
        analyzed: false
      }
    ];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should convert a boolean into a string: true', () => {
    expect(component.booleanToString(true)).toBe('yes');
  });

  it('should convert a boolean into a string: false', () => {
    expect(component.booleanToString(false)).toBe('no');
  });

  it('should convert a timestamp into a string', () => {
    expect(component.timestampToDate(1576832400000)).toBe('20.12.2019');
  });

  it('should convert trim project name: all commits', () => {
    component.project = {
      id: 1,
      name: 'test',
      vcsUrl: 'https://valid.url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: 'first commit',
      endDate: 'current'
    };
    expect(component.getTitleText()).toBe('Showing commits for project test (2)');
  });

  it('should convert trim project name: from date', () => {
    component.project = {
      id: 1,
      name: 'test',
      vcsUrl: 'https://valid.url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: '16.12.2019',
      endDate: 'current'
    };
    expect(component.getTitleText()).toBe('Showing commits for project test from 16.12.2019 up until today (2)');
  });

  it('should convert trim project name: to date', () => {
    component.project = {
      id: 1,
      name: 'test',
      vcsUrl: 'https://valid.url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: 'first commit',
      endDate: '22.12.2019'
    };
    expect(component.getTitleText()).toBe('Showing commits for project test from 20.12.2019 up until 22.12.2019 (2)');
  });

  it('should convert trim project name: from date to date', () => {
    component.project = {
      id: 1,
      name: 'test',
      vcsUrl: 'https://valid.url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: '16.12.2019',
      endDate: '22.12.2019'
    };
    expect(component.getTitleText()).toBe('Showing commits for project test from 16.12.2019 up until 22.12.2019 (2)');
  });

  it('should start complexity analysis', () => {
    component.selectedCommit1 = component.commits[1];
    component.selectedCommit2 = component.commits[0];
    component.startComplexityAnalysis();
    fixture.whenStable().then(() => {
      expect(routerSpy).toHaveBeenCalledWith(['/city/1']);
    });
  });
});

class MockUserService extends UserService {
  refresh(callback: () => any) {
    callback();
  }
}
