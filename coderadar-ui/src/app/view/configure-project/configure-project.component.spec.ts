import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {ConfigureProjectComponent} from './configure-project.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserService} from '../../service/user.service';
import {RouterTestingModule} from '@angular/router/testing';
import {of} from 'rxjs';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AppComponent} from '../../app.component';
import {HttpClientModule, HttpResponse} from '@angular/common/http';
import {ProjectService} from '../../service/project.service';
import {Module} from '../../model/module';

describe('ConfigureProjectComponent', () => {
  let component: ConfigureProjectComponent;
  let fixture: ComponentFixture<ConfigureProjectComponent>;
  const mockSnackbar = jasmine.createSpyObj(['open']);
  let http;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfigureProjectComponent],
      imports: [
        FormsModule, // ngModel
        RouterTestingModule,
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [
        {provide: MatSnackBar, useValue: mockSnackbar},
        {
          provide: ActivatedRoute, useValue: {
            params: of({id: 1})
          }
        },
        {provide: ProjectService, useClass: MockProjectService}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ConfigureProjectComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add module', () => {
    expect(component.modules.length).toBe(0);
    component.modulesInput = '/src/main/java/testDir';
    component.projectId = 1;
    component.submitModule();
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush({id: 1}, {
      status: 201,
      url: '/projects/1/modules',
      statusText: 'Created',
    });
    fixture.whenStable().then(() => {
      expect(component.modules.length).toBe(1);
      expect(component.modules[0].path).toBe('/src/main/java/testDir');
      expect(component.modules[0].id).toBe(1);
    });
  });

  it('should get unauthenticated and call userService for new authentication on add module',
    inject([UserService], (userService: UserService) => {
      component.modulesInput = '/src/main/java/testDir';
      component.projectId = 1;
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      component.submitModule();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush({
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

  it('should get conflict on add module', () => {
    component.modulesInput = '/src/main/java/testDir';
    component.projectId = 1;
    component.submitModule();
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush({
      status: 409,
      error: 'Conflict',
      errorMessage: 'The module test already exists.',
      path: '/projects/1/modules'
    }, {
      status: 409,
      statusText: 'Conflict',
      url: '/projects/1/modules',
    });
    fixture.whenStable().then(() => {
      expect(component.moduleExists).toBeTruthy();
    });
  });

  it('should get Unprocessable Entity on adding a module', () => {
    component.modulesInput = '/src/main/java/testDir';
    component.projectId = 1;
    component.submitModule();
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      errorMessage: 'The project test already exists.',
      path: '/projects/1/modules'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects/1/modules',
    });
    fixture.whenStable().then(() => {
      expect(mockSnackbar.open).toHaveBeenCalledWith('Cannot edit the project! Try again later', '🞩', {duration: 4000});
    });
  });

  it('should add file pattern', () => {
    expect(component.filePatterns.length).toBe(0);
    component.filePatternIncludeInput = '**/*.java';
    component.projectId = 1;
    component.submitFilePattern('INCLUDE');
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush({id: 1}, {
      status: 201,
      url: '/projects/1/filePatterns',
      statusText: 'Created',
    });
    fixture.whenStable().then(() => {
      expect(component.filePatterns.length).toBe(1);
      expect(component.filePatterns[0].pattern).toBe('**/*.java');
      expect(component.filePatterns[0].id).toBe(1);
      expect(component.filePatternIncludeInput).toBe('');
      expect(component.filePatternExcludeInput).toBe('');
    });
  });

  it('should get unauthenticated on file pattern and call userService for new authentication on add module',
    inject([UserService], (userService: UserService) => {
      component.filePatternIncludeInput = '**/*.java';
      component.projectId = 1;
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {});
      component.submitFilePattern('INCLUDE');
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects/1/filePatterns'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects/1/filePatterns',
      });
      fixture.whenStable().then(() => {
        expect(refreshSpy).toHaveBeenCalled();
      });
    })
  );
});

class MockProjectService extends ProjectService {
  getProjectModules(id: number): Promise<HttpResponse<Module[]>> {
    return of(new HttpResponse({body: [] as Module[]})).toPromise();
  }

  getProjectFilePatterns(id: number): Promise<HttpResponse<any>> {
    return of(new HttpResponse({body: [] as Module[]})).toPromise();
  }
}
