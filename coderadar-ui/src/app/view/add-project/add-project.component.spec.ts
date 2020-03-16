import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {AddProjectComponent} from './add-project.component';
import {BrowserModule, By} from '@angular/platform-browser';
import {HttpClientModule, HttpResponse} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FlexLayoutModule} from '@angular/flex-layout';
import {LayoutModule} from '@angular/cdk/layout';
import {ControlPanelModule} from '../../city-map/control-panel/control-panel.module';
import {VisualizationModule} from '../../city-map/visualization/visualization.module';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatExpansionModule} from '@angular/material/expansion';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {UserService} from '../../service/user.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {ConfigureProjectComponent} from '../configure-project/configure-project.component';
import {AppComponent} from '../../app.component';
import {EditProjectComponent} from '../edit-project/edit-project.component';

const project = {
  id: null,
  name: 'test',
  vcsUrl: 'https://valid.url',
  vcsUsername: '',
  vcsPassword: '',
  vcsOnline: true,
  startDate: null,
  endDate: null
};
let routerSpy;
let nameInput;
let vcsUrlInput;
let vcsUserInput;
let vcsPasswordInput;
let startDateInput;
let endDateInput;
let button;
let fixture: ComponentFixture<AddProjectComponent>;

describe('AddProjectComponent', () => {
  let component: AddProjectComponent;
  let http;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        AddProjectComponent,
        ConfigureProjectComponent
      ],
      imports: [
        BrowserModule,
        FormsModule,
        BrowserAnimationsModule,
        BrowserModule,
        FontAwesomeModule,
        BrowserAnimationsModule,
        FlexLayoutModule,
        MatFormFieldModule,
        MatInputModule,
        MatCardModule,
        MatSnackBarModule,
        MatButtonModule,
        ReactiveFormsModule,
        MatGridListModule,
        MatMenuModule,
        MatListModule,
        MatIconModule,
        RouterModule,
        LayoutModule,
        MatToolbarModule,
        MatSidenavModule,
        MatCheckboxModule,
        BrowserModule,
        FormsModule,
        HttpClientModule,
        ControlPanelModule,
        VisualizationModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatExpansionModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([
          {path: 'project-configure/:id', component: ConfigureProjectComponent},
        ]),
      ],
      providers: [
        {provide: Router},
        {provide: ActivatedRoute},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AddProjectComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get unauthenticated and call userService for new authentication',
    inject([UserService], (userService: UserService) => {
      component.project = project;
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {});
      component.submitForm();
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
        expect(refreshSpy).toHaveBeenCalled();
        http.verify();
      });
    })
  );

  it('should get conflict', () => {
    component.project = project;
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}projects`).flush({
      status: 409,
      error: 'Conflict',
      errorMessage: 'The project test already exists.',
      path: '/projects'
    }, {
      status: 409,
      statusText: 'Conflict',
      url: '/projects',
    });
    fixture.whenStable().then(() => {
      expect(component.incorrectURL).toBeFalsy();
      expect(component.projectExists).toBeTruthy();
    });
  });

  it('should get bad request', () => {
    component.project = project;
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}projects`).flush({
      status: 400,
      error: 'Bad Request',
      errorMessage: 'Validation Error',
      path: '/projects',
      fieldErrors: [
        {field: 'vcsUrl'}
      ]
    }, {
      status: 400,
      statusText: 'Bad Request',
      url: '/projects',
    });
    fixture.whenStable().then(() => {
      expect(component.incorrectURL).toBeTruthy();
      expect(component.projectExists).toBeFalsy();
    });
  });

  it('should submit form', () => {
    component.project = project;
    component.submitForm();
    http.expectOne(`${AppComponent.getApiUrl()}projects`).flush({id: 1}, {
      status: 201,
      url: '/projects',
      statusText: 'Created',
    });
    fixture.whenStable().then(() => {
      expect(component.incorrectURL).toBeFalsy();
      expect(component.projectExists).toBeFalsy();
      expect(component.project.id).toBe(1);
    });
  });

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

  // TODO fix url validation
  xit('should fail validation of new project data because of url', () => {
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
  });
});

describe('EditProjectComponent', () => {
  let component: AddProjectComponent;
  let submitSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddProjectComponent],
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

    fixture = TestBed.createComponent(AddProjectComponent);
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

  it('should create component in HTML', () => {
    fixture.whenStable().then(() => {
      setValues('test', 'https://github.com/reflectoring/coderadar', undefined, '', null, null);
      expect(component.project.name).toBe('test');
      button.click();
      expect(submitSpy).toHaveBeenCalled();
    });
  });

  it('should create component name empty in HTML', () => {
    setValues('', 'https://github.com/reflectoring/coderadar', undefined, '', null, null);
    button.click();
    expect(submitSpy).toHaveBeenCalled();
    component.nameEmpty = true;
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-error')).nativeElement.innerText)
      .toBe('The project name must not be empty!');
  });

  // TODO fix url validation
  xit('should create component invalid url in HTML', () => {
    setValues('test', 'fsdfsfsdf', undefined, '', null, null);
    button.click();
    expect(submitSpy).toHaveBeenCalled();
    component.incorrectURL = true;
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('mat-error')).nativeElement.innerText)
      .toEqual('The VCS URL is invalid!');
  });

  it('should create component name already exists in HTML', () => {
    setValues('test', 'https://github.com/reflectoring/coderadar', undefined, '', null, null);
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

  nameInput.dispatchEvent(new Event('input'));
  vcsUrlInput.dispatchEvent(new Event('input'));
  vcsUserInput.dispatchEvent(new Event('input'));
  vcsPasswordInput.dispatchEvent(new Event('input'));
  startDateInput.dispatchEvent(new Event('input'));
  endDateInput.dispatchEvent(new Event('input'));
  fixture.detectChanges();
}
