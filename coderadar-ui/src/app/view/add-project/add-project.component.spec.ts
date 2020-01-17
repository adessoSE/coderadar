import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {AddProjectComponent} from './add-project.component';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
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
import {MatSnackBarModule} from '@angular/material/snack-bar';
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

describe('AddProjectComponent', () => {
  let component: AddProjectComponent;
  let fixture: ComponentFixture<AddProjectComponent>;
  let routerSpy;

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
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get unauthenticated and call userService for new authentication',
    inject([UserService], (userService: UserService) => {
      component.project = project;
      const http = TestBed.get(HttpTestingController);
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
      });
    })
  );

  it('should get conflict', () => {
    component.project = project;
    const http = TestBed.get(HttpTestingController);
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
    const http = TestBed.get(HttpTestingController);
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
    const http = TestBed.get(HttpTestingController);
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
});
