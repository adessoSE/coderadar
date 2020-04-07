import {async, ComponentFixture, fakeAsync, inject, TestBed, tick} from '@angular/core/testing';

import {EditProjectComponent} from './edit-project.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {HttpClientModule, HttpResponse} from '@angular/common/http';
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
import {By, Title} from '@angular/platform-browser';
import {Project} from '../../model/project';

let nameInput;
let vcsUrlInput;
let vcsUserInput;
let vcsPasswordInput;
let startDateInput;
let endDateInput;
let button;
let fixture: ComponentFixture<EditProjectComponent>;

describe('EditProjectComponent', () => {
  let component: EditProjectComponent;
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
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      component.project = project;
      component.submitForm();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
        status: 200,
        url: '/projects/1',
        statusText: 'Ok',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
          expect(component.incorrectURL).toBeFalsy();
          expect(component.projectExists).toBeFalsy();
          expect(mockSnackbar.open).toHaveBeenCalledWith('Project successfully edited!', '🞩', {duration: 4000});
        });
      });
    });
  });

  it('should edit project forbidden',
    inject([UserService], (userService: UserService) => {
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1',
      });
      fixture.whenStable().then(() => {
        component.project = project;
        const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
        });
        component.submitForm();
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
    })
  );

  it('should edit project conflict', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
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
        fixture.whenStable().then(() => {
          expect(component.projectExists).toBeTruthy();
        });
      });
    });
  });

  it('should edit project bad request', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
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
        fixture.whenStable().then(() => {
          expect(component.incorrectURL).toBeTruthy();
          expect(component.projectExists).toBeFalsy();
        });
      });
    });
  });

  it('should edit a project unprocessable entity', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
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
        fixture.whenStable().then(() => {
          expect(mockSnackbar.open).toHaveBeenCalledWith('Project cannot be edited! Try again later!', '🞩', {duration: 4000});
        });
      });
    });
  });

  it('should validate input', () => {
    component.project = {
      id: 1,
      name: 'test',
      vcsUrl: 'https://valid.url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: null,
      endDate: null
    };
    const valid = (component as any).validateInput();
    expect(valid).toBeFalsy();
    expect(component.nameEmpty).toBeFalsy();
    expect(component.incorrectURL).toBeFalsy();
  });

  it('should validate input startDate first commit', () => {
    component.project = {
      id: 1,
      name: 'test',
      vcsUrl: 'https://valid.url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: 'first commit',
      endDate: null
    };
    const valid = (component as any).validateInput();
    expect(valid).toBeFalsy();
    expect(component.nameEmpty).toBeFalsy();
    expect(component.incorrectURL).toBeFalsy();
    expect(component.project.startDate).toBe(null);
  });

  it('should validate input endDate current', () => {
    component.project = {
      id: 1,
      name: 'test',
      vcsUrl: 'https://valid.url',
      vcsUsername: '',
      vcsPassword: '',
      vcsOnline: true,
      startDate: null,
      endDate: 'current'
    };
    const valid = (component as any).validateInput();
    expect(valid).toBeFalsy();
    expect(component.nameEmpty).toBeFalsy();
    expect(component.incorrectURL).toBeFalsy();
    expect(component.project.endDate).toBe(null);
  });

  // TODO fix url validation
  xit('should validate input empty name invalid url', () => {
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
    const valid = (component as any).validateInput();
    expect(valid).toBeTruthy();
    expect(component.incorrectURL).toBeTruthy();
  });

  it('should validate input empty name', () => {
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
    const valid = (component as any).validateInput();
    expect(valid).toBeTruthy();
    expect(component.nameEmpty).toBeTruthy();
  });

  it('should get project', inject([Title], (titleService: Title) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      const titleSpy = spyOn(titleService, 'setTitle').and.callFake(callback => {
      });
      component.projectId = 1;
      (component as any).getProject();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush(project, {
        status: 200,
        url: '/projects/1',
        statusText: 'Ok',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(JSON.stringify(component.project)).toBe(JSON.stringify(new Project(project)));
          expect(component.projectName).toBe('test');
          expect(titleSpy).toHaveBeenCalledWith('Coderadar - Edit test');
        });
      });
    });
  }));

  it('should get project forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      component.projectId = 1;
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

  it('should get project not found', () => {
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
        path: '/projects/1'
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
});

describe('EditProjectComponent', () => {
  let component: EditProjectComponent;
  let submitSpy;
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
      declarations: [EditProjectComponent],
      imports: [
        FormsModule, // ngModel
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [
        {provide: MatSnackBar}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EditProjectComponent);
    component = fixture.componentInstance;
    submitSpy = spyOn(component, 'submitForm').and.callFake(() => {});
    fixture.detectChanges();
    nameInput = fixture.debugElement.query(By.css('input[name="name"]')).nativeElement;
    vcsUrlInput = fixture.debugElement.query(By.css('input[name="vcsUrl"]')).nativeElement;
    vcsUserInput = fixture.debugElement.query(By.css('input[name="vcsUser"]')).nativeElement;
    vcsPasswordInput = fixture.debugElement.query(By.css('input[name="vcsPassword"]')).nativeElement;
    startDateInput = fixture.debugElement.query(By.css('input[name="startDate"]')).nativeElement;
    endDateInput = fixture.debugElement.query(By.css('input[name="endDate"]')).nativeElement;
    button = fixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
    return fixture.whenStable().then(() => {
      fixture.detectChanges();
    });
  });

  it('should edit component in HTML', () => {
    // fixture.whenStable().then(() => {
      setValues('test2', 'https://github.com/reflectoring/coderadar', undefined, '', null, null);
      expect(component.project.name).toBe('test2');
      button.click();
      expect(submitSpy).toHaveBeenCalled();
    // });
  });

  it('should edit component name empty in HTML', () => {
    setValues('', 'http://valid.url', undefined, '', null, null);
    button.click();
    expect(submitSpy).toHaveBeenCalled();
    component.nameEmpty = true;
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-error')).nativeElement.innerText)
      .toBe('The project name must not be empty!');
  });

  // TODO fix url validation
  xit('should edit component invalid url in HTML', () => {
    setValues('test2', 'jhsbdfls', undefined, '', null, null);
    button.click();
    expect(submitSpy).toHaveBeenCalled();
    component.incorrectURL = true;
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-error')).nativeElement.innerText)
      .toEqual('The VCS URL is invalid!');
  });

  it('should edit component name already exists in HTML', () => {
    setValues('test2', 'http://valid.url', undefined, '', null, null);
    button.click();
    expect(submitSpy).toHaveBeenCalled();
    component.projectExists = true;
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-error')).nativeElement.innerText)
      .toEqual('A project with this name already exists! Please choose another name.');
  });
});

function setValues(name, vcsUrl, vcsUser, vcsPassword, startDate, endDate) {
  nameInput.value = name;
  vcsUrlInput.value = vcsUrl;
  vcsUserInput.value = vcsUser;
  vcsPasswordInput.value = vcsPassword;
  startDateInput.value = startDate;
  endDateInput.value = endDate;

  nameInput.dispatchEvent(new Event( 'input'));
  vcsUrlInput.dispatchEvent(new Event('input'));
  vcsUserInput.dispatchEvent(new Event('input'));
  vcsPasswordInput.dispatchEvent(new Event('input'));
  startDateInput.dispatchEvent(new Event('input'));
  endDateInput.dispatchEvent(new Event('input'));
  fixture.detectChanges();
}
