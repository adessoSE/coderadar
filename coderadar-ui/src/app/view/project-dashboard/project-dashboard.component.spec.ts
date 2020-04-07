import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

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
import {HttpClientModule} from '@angular/common/http';
import {Actions} from '@ngrx/effects';
import {CityViewComponent} from '../city-view/city-view.component';
import {AppComponent} from '../../app.component';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {By, Title} from '@angular/platform-browser';
import {detectChanges} from "@angular/core/src/render3";
import {PageEvent} from "@angular/material/paginator";

describe('ProjectDashboardComponent', () => {
  let component: ProjectDashboardComponent;
  let fixture: ComponentFixture<ProjectDashboardComponent>;
  let routerSpy;
  let http;
  const commits = [
    {
      name: 'init',
      author: 'testUser',
      comment: 'initial commit',
      timestamp: 1576832400000,
      analyzed: true
    },
    {
      name: 'second',
      author: 'testUser',
      comment: 'second commit',
      timestamp: 1576832800000,
      analyzed: true
    }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        ProjectDashboardComponent,
        CityViewComponent
      ],
      imports: [
        HttpClientModule,
        HttpClientTestingModule,
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
        Actions,
        ActionsSubject,
        AppEffects,
        {provide: ReducerManager},
        ReducerManagerDispatcher,
        ScannedActionsSubject,
        Store
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ProjectDashboardComponent);
    http = TestBed.get(HttpTestingController);
    component = fixture.componentInstance;
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {
    });
    component.commits = commits;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should boolean to string true', () => {
    expect(component.booleanToString(true)).toBe('yes');
  });

  it('should boolean to string false', () => {
    expect(component.booleanToString(false)).toBe('no');
  });

  it('should timestamp to string', () => {
    expect(component.timestampToDate(1576832400000)).toBe('20.12.2019');
  });

  it('should get title all commits', () => {
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

  it('should get title from date', () => {
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

  it('should get title to date', () => {
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

  it('should get title from date to date', () => {
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

  it('should get commits', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      component.selectedCommit1 = commits[0];
      component.selectedCommit2 = commits[1];
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([commits], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.selectedCommit1.name).toBe('initial commit');
          expect(component.selectedCommit1.analyzed).toBe(true);
          expect(component.selectedCommit2.name).toBe('second commit');
          expect(component.selectedCommit2.analyzed).toBe(true);
          expect(component.commitsAnalyzed).toBe(2);
        });
      });
    });
  });

  it('should get commits no selectedCommit1', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      component.selectedCommit2 = commits[1];
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([commits], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.selectedCommit1).toBe(null);
          expect(component.selectedCommit2.name).toBe('second commit');
          expect(component.selectedCommit2.analyzed).toBe(true);
          expect(component.commitsAnalyzed).toBe(1);
        });
      });
    });
  });

  it('should get commits no selectedCommit2', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      component.selectedCommit1 = commits[0];
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([commits], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.selectedCommit1.name).toBe('initial commit');
          expect(component.selectedCommit1.analyzed).toBe(true);
          expect(component.selectedCommit2).toBe(null);
          expect(component.commitsAnalyzed).toBe(2);
        });
      });
    });
  });

  it('should get commits no selectedCommit1 and selectedCommit2', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      component.selectedCommit2 = commits[1];
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([commits], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.selectedCommit1).toBe(null);
          expect(component.selectedCommit2).toBe(null);
          expect(component.commitsAnalyzed).toBe(2);
        });
      });
    });
  });

  it('should get commits no commits analyzed', () => {
    commits[0].analyzed = false;
    commits[1].analyzed = false;
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      component.selectedCommit1 = commits[0];
      component.selectedCommit2 = commits[1];
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([commits], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.selectedCommit1.name).toBe('initial commit');
          expect(component.selectedCommit1.analyzed).toBe(false);
          expect(component.selectedCommit2.name).toBe('second commit');
          expect(component.selectedCommit2.analyzed).toBe(false);
          expect(component.commitsAnalyzed).toBe(0);
        });
      });
    });
  });

  it('should get commits one commit analyzed', () => {
    commits[0].analyzed = false;
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      component.selectedCommit1 = commits[0];
      component.selectedCommit2 = commits[1];
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([commits], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.selectedCommit1.name).toBe('initial commit');
          expect(component.selectedCommit1.analyzed).toBe(true);
          expect(component.selectedCommit2.name).toBe('second commit');
          expect(component.selectedCommit2.analyzed).toBe(false);
          expect(component.commitsAnalyzed).toBe(1);
        });
      });
    });
  });

  it('should get commits no commits analyzed', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      commits[0].analyzed = false;
      commits[1].analyzed = false;
      component.projectId = 1;
      component.selectedCommit1 = commits[0];
      component.selectedCommit2 = commits[1];
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([{
        name: 'init',
        author: 'testUser',
        comment: 'initial commit',
        timestamp: 1576832400000,
        analyzed: true
      }], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.selectedCommit1.name).toBe('initial commit');
          expect(component.selectedCommit1.analyzed).toBe(true);
          expect(component.selectedCommit2).toBe(null);
          expect(component.commitsAnalyzed).toBe(1);
        });
      });
    });
  });

  it('should get commits no commits analyzed', () => {
    commits[0].analyzed = false;
    commits[1].analyzed = false;
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      component.selectedCommit1 = commits[0];
      component.selectedCommit2 = commits[1];
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.selectedCommit1).toBe(null);
          expect(component.selectedCommit2).toBe(null);
          expect(component.commitsAnalyzed).toBe(0);
        });
      });
    });
  });

  it('should get commits no commits analyzed', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/commits',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      component.selectedCommit1 = commits[0];
      component.selectedCommit2 = commits[1];
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      (component as any).getCommits();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/commits`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects/1/commits'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects/1/commits',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(refreshSpy).toHaveBeenCalled();
        });
      });
    });
  }));

  it('should get projects', inject([Title], (titleService: Title) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      const titleSpy = spyOn(titleService, 'setTitle').and.callFake(callback => {
      });
      (component as any).getProject();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({
        id: 1,
        name: 'test',
        vcsUrl: 'https://valid.url',
        vcsUsername: '',
        vcsPassword: '',
        vcsOnline: true,
        startDate: null,
        endDate: null
      }, {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(titleSpy).toHaveBeenCalledWith('Coderadar - test');
        });
      });
    });
  }));

  it('should get projects forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
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
        fixture.whenStable().then(() => {
          expect(refreshSpy).toHaveBeenCalled();
        });
      });
    });
  }));

  it('should get projects not found', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
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
        fixture.whenStable().then(() => {
          expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
        });
      });
    });
  });

  it('should select card select selectedCommit1', () => {
    component.selectedCommit1 = null;
    component.selectedCommit2 = commits[1];
    const selectedCommit = commits[0];
    component.selectCard(selectedCommit);
    expect(component.selectedCommit1).toBe(selectedCommit);
  });

  it('should select card deselect selectedCommit1', () => {
    component.selectedCommit1 = commits[0];
    component.selectedCommit2 = commits[1];
    const selectedCommit = commits[0];
    component.selectCard(selectedCommit);
    expect(component.selectedCommit1).toBe(null);
  });

  it('should select card deselect selectedCommit1 no second commit', () => {
    component.selectedCommit1 = commits[0];
    component.selectedCommit2 = null;
    const selectedCommit = commits[0];
    component.selectCard(selectedCommit);
    expect(component.selectedCommit1).toBe(null);
  });

  it('should select card deselect selectedCommit2', () => {
    component.selectedCommit1 = commits[0];
    component.selectedCommit2 = commits[1];
    const selectedCommit = commits[1];
    component.selectCard(selectedCommit);
    expect(component.selectedCommit2).toBe(null);
  });

  it('should select card deselect selectedCommit1 no second commit', () => {
    component.selectedCommit1 = null;
    component.selectedCommit2 = commits[1];
    const selectedCommit = commits[1];
    component.selectCard(selectedCommit);
    expect(component.selectedCommit2).toBe(null);
  });

  it('should select card select selectedCommit1', () => {
    component.selectedCommit1 = commits[0];
    component.selectedCommit2 = null;
    const selectedCommit = commits[1];
    component.selectCard(selectedCommit);
    expect(component.selectedCommit2).toBe(selectedCommit);
  });
});

describe('ProjectDashboardComponent', () => {
  let component: ProjectDashboardComponent;
  let fixture: ComponentFixture<ProjectDashboardComponent>;
  const commits = [
    {
      name: 'init',
      author: 'testUser',
      comment: 'initial commit',
      timestamp: 1576832400000,
      analyzed: true
    },
    {
      name: 'second',
      author: 'testUser',
      comment: 'second commit',
      timestamp: 1576832800000,
      analyzed: true
    }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectDashboardComponent],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [
        {
          provide: ActivatedRoute, useValue: {
            params: of({id: 1})
          }
        },
        {provide: StateObservable},
        Actions,
        ActionsSubject,
        AppEffects,
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
  });

  it('should display two commits', () => {
    component.commits = commits;
    fixture.detectChanges();
    expect(fixture.debugElement.queryAll(By.css('mat-accordion')).length).toBe(2);
    const commit1 = fixture.debugElement.queryAll(By.css('mat-accordion'))[0].nativeElement;
    const commit2 = fixture.debugElement.queryAll(By.css('mat-accordion'))[1].nativeElement;
    expect(commit1.querySelectorAll('mat-panel-description')[0].innerText).toBe('init ');
    expect(commit1.querySelectorAll('mat-panel-description')[1].innerText).toBe('testUser');
    expect(commit1.querySelectorAll('mat-panel-description')[2].innerText).toBe('initial commit');
    expect(commit1.querySelectorAll('mat-panel-description')[3].innerText).toBe('20.12.2019');

    expect(commit2.querySelectorAll('mat-panel-description')[0].innerText).toBe('second ');
    expect(commit2.querySelectorAll('mat-panel-description')[1].innerText).toBe('testUser');
    expect(commit2.querySelectorAll('mat-panel-description')[2].innerText).toBe('second commit');
    expect(commit2.querySelectorAll('mat-panel-description')[3].innerText).toBe('20.12.2019');
  });

  it('should display complexity analysis button', () => {
    component.commits = commits;
    component.selectedCommit1 = commits[0];
    component.selectedCommit2 = commits[1];
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-icon')).nativeElement).toBeTruthy();
  });

  it('should display complexity analysis button only selectedCommit1', () => {
    component.commits = commits;
    component.selectedCommit1 = commits[0];
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-icon'))).toBeFalsy();
  });

  it('should display complexity analysis button  only selectedCommit2', () => {
    component.commits = commits;
    component.selectedCommit2 = commits[1];
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-icon'))).toBeFalsy();
  });

  it('should display complexity analysis button selectedCommit1 not analyzed', () => {
    component.commits = commits;
    component.selectedCommit1 = {
      name: 'init',
      author: 'testUser',
      comment: 'initial commit',
      timestamp: 1576832400000,
      analyzed: false
    };
    component.selectedCommit2 = commits[1];
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-icon'))).toBeFalsy();
  });

  it('should display complexity analysis button selectedCommit2 not analyzed', () => {
    component.commits = commits;
    component.selectedCommit1 = commits[0];
    component.selectedCommit2 = {
      name: 'second',
      author: 'testUser',
      comment: 'second commit',
      timestamp: 1576832400000,
      analyzed: false
    };
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-icon'))).toBeFalsy();
  });

  it('should handle paginators', () => {
    component.commits = [];
    for (let i = 0; i < 20; i++) {
      component.commits.push({
        name: `c ${i}`,
        author: 'testUser',
        comment: `commit ${i}`,
        timestamp: 1576832400000 + (i * 86400000),
        analyzed: false
      });
    }
    expect(component.commits.length).toBe(20);
    fixture.detectChanges();
    expect(fixture.debugElement.queryAll(By.css('mat-accordion')).length).toBe(15);
    const commit1 = fixture.debugElement.queryAll(By.css('mat-accordion'))[0].nativeElement;
    expect(commit1.querySelectorAll('mat-panel-description')[0].innerText).toBe('c 0 ');
    expect(commit1.querySelectorAll('mat-panel-description')[1].innerText).toBe('testUser');
    expect(commit1.querySelectorAll('mat-panel-description')[2].innerText).toBe('commit 0');
    expect(commit1.querySelectorAll('mat-panel-description')[3].innerText).toBe('20.12.2019');
    const pageEvent = new PageEvent();
    pageEvent.pageIndex = 1;
    pageEvent.length = 20;
    pageEvent.previousPageIndex = 0;
    pageEvent.pageSize = 15;
    component.syncPaginators(pageEvent);
    fixture.detectChanges();
    const commit15 = fixture.debugElement.queryAll(By.css('mat-accordion'))[0].nativeElement;
    expect(commit15.querySelectorAll('mat-panel-description')[0].innerText).toBe('c 15 ');
    expect(commit15.querySelectorAll('mat-panel-description')[1].innerText).toBe('testUser');
    expect(commit15.querySelectorAll('mat-panel-description')[2].innerText).toBe('commit 15');
    expect(commit15.querySelectorAll('mat-panel-description')[3].innerText).toBe('4.1.2020');
  });
});
