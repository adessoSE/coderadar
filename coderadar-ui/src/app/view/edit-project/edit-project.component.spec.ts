import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {EditProjectComponent} from './edit-project.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {HttpClient, HttpClientModule, HttpHandler, HttpResponse} from '@angular/common/http';
import {ProjectService} from '../../service/project.service';
import {Project} from '../../model/project';
import {RouterTestingModule} from '@angular/router/testing';
import {MainDashboardComponent} from '../main-dashboard/main-dashboard.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AppComponent} from '../../app.component';

describe('EditProjectComponent', () => {
  let component: EditProjectComponent;
  let fixture: ComponentFixture<EditProjectComponent>;
  let routerSpy;
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
      declarations: [
        EditProjectComponent,
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
        HttpClientModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([
          {path: 'dashboard', component: MainDashboardComponent},
        ]),
      ],
      providers: [
        {provide: ProjectService, useClass: MockProjectService},
        {
          provide: ActivatedRoute, useValue: {
            params: of({id: 1})
          }
        },
        {provide: MatSnackBar, useValue: mockSnackbar}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EditProjectComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {
    });
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should edit project', () => {
    component.project = project;
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      url: '/projects/1',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
      expect(component.incorrectURL).toBeFalsy();
      expect(component.projectExists).toBeFalsy();
      expect(mockSnackbar.open).toHaveBeenCalledWith('Project successfully edited!', '🞩', {duration: 4000});
    });
  });

  it('should get unauthenticated and call userService for new authentication on add module',
    inject([UserService], (userService: UserService) => {
      component.project = project;
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      component.submitForm();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects/1/modules'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects/1/modules',
      });
      fixture.whenStable().then(() => {
        expect(refreshSpy).toHaveBeenCalled();
      });
    })
  );

  it('should get conflict on edit project', () => {
    component.project = project;
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({
      status: 409,
      error: 'Conflict',
      errorMessage: 'Project with name \'test\' already exists. Please choose another name.',
      path: '/projects/1'
    }, {
      status: 409,
      statusText: 'Conflict',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      expect(component.projectExists).toBeTruthy();
    });
  });

  it('should get bad request on edit project', () => {
    component.project = project;
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({
      status: 400,
      error: 'Bad Request',
      errorMessage: 'Validation Error',
      path: '/projects/1',
      fieldErrors: [
        {field: 'vcsUrl'}
      ]
    }, {
      status: 400,
      statusText: 'Bad Request',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      expect(component.incorrectURL).toBeTruthy();
      expect(component.projectExists).toBeFalsy();
    });
  });

  it('should get Unprocessable Entity on editing a project', () => {
    component.project = project;
    component.submitForm();
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
      expect(mockSnackbar.open).toHaveBeenCalledWith('Project cannot be edited! Try again later!', '🞩', {duration: 4000});
    });
  });

  // TODO fix url validation
  /*it('should fail validation of new project data because of url', () => {
    component.project = {
      id: 1,
      name: 'test',
      vcsUrl: 'an invalid url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: null,
      endDate: null
    };
    component.submitForm();
    fixture.whenStable().then(() => {
      expect(component.incorrectURL).toBeTruthy();
    });
  });*/

  it('should fail validation of new project data because of empty name', () => {
    component.project = {
      id: 1,
      name: '',
      vcsUrl: 'https://valid.url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: null,
      endDate: null
    };
    component.submitForm();
    fixture.whenStable().then(() => {
      expect(component.nameEmpty).toBeTruthy();
    });
  });
});

class MockProjectService extends ProjectService {
  getProject(id: number): Promise<HttpResponse<any>> {
    return of(new HttpResponse({
      body: {
        id: 1,
        name: 'test',
        vcsUrl: 'https://valid.url',
        vcsUsername: '',
        vcsPassword: '',
        vcsOnline: true,
        startDate: null,
        endDate: null
      }
    })).toPromise();
  }
}
