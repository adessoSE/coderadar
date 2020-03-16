import {ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {ConfigureProjectComponent} from './configure-project.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserService} from '../../service/user.service';
import {RouterTestingModule} from '@angular/router/testing';
import {of} from 'rxjs';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AppComponent} from '../../app.component';
import {HttpClientModule, HttpResponse} from '@angular/common/http';
import {By, Title} from '@angular/platform-browser';
import {AnalyzerConfiguration} from '../../model/analyzer-configuration';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {ProjectService} from '../../service/project.service';
import {Module} from '../../model/module';
import {FilePattern} from '../../model/file-pattern';

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
        BrowserAnimationsModule,
        MatCheckboxModule,
        HttpClientTestingModule
      ],
      providers: [
        {provide: MatSnackBar, useValue: mockSnackbar},
        {
          provide: ActivatedRoute, useValue: {
            params: of({id: 1})
          }
        }
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
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush([], {
      status: 200,
      url: '/projects/1/modules',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
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
  });

  it('should add module forbidden', inject([UserService], (userService: UserService) => {
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush([], {
        status: 200,
        url: '/projects/1/modules',
        statusText: 'Ok',
      });
      fixture.whenStable().then(() => {
        component.modulesInput = '/src/main/java/testDir';
        component.projectId = 1;
        const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {});
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
          fixture.whenStable().then(() => {
            expect(refreshSpy).toHaveBeenCalled();
          });
        });
      });
  }));

  it('should add module conflict', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush([], {
      status: 200,
      url: '/projects/1/modules',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
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
        fixture.whenStable().then(() => {
          expect(component.moduleExists).toBeTruthy();
        });
      });
    });
  });

  it('should add module unprocessable entity', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush([], {
      status: 200,
      url: '/projects/1/modules',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
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
        fixture.whenStable().then(() => {
          expect(mockSnackbar.open).toHaveBeenCalledWith('Cannot edit the project! Try again later', '🞩', {duration: 4000});
        });
      });
    });
  });

  it('should add file pattern', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush([], {
      status: 200,
      url: '/projects/1/modules',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
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
        fixture.whenStable().then(() => {
          expect(component.filePatterns.length).toBe(1);
          expect(component.filePatterns[0].pattern).toBe('**/*.java');
          expect(component.filePatterns[0].id).toBe(1);
          expect(component.filePatternIncludeInput).toBe('');
          expect(component.filePatternExcludeInput).toBe('');
        });
      });
    });
  });

  it('should add file pattern forbidden', inject([UserService], (userService: UserService) => {
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush([], {
        status: 200,
        url: '/projects/1/modules',
        statusText: 'Ok',
      });
      fixture.whenStable().then(() => {
        component.filePatternIncludeInput = '**/*.java';
        component.projectId = 1;
        const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
        });
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
          fixture.whenStable().then(() => {
            expect(refreshSpy).toHaveBeenCalled();
          });
        });
      });
    })
  );

  it('should get modules for project', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush([], {
      status: 200,
      url: '/projects/1/modules',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      (component as any).getModulesForProject();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush([{
        id: 1,
        path: '/src/main/java/testDir'
      }], {
        status: 200,
        url: '/projects/1/modules',
        statusText: 'Ok',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.modules.length).toBe(1);
        });
      });
    });
  });

  it('should get modules for project forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules`).flush([], {
      status: 200,
      url: '/projects/1/modules',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      component.projectId = 1;
      (component as any).getModulesForProject();
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
        fixture.whenStable().then(() => {
          expect(refreshSpy).toHaveBeenCalled();
        });
      });
    });
  }));

  it('should get project analyzer', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush([], {
      status: 200,
      url: '/projects/1/modules',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      const availableAnalyzerSpy = spyOn((ConfigureProjectComponent.prototype as any), 'getAvailableAnalyzers')
        .and.callFake(callback => {});
      (component as any).getProjectAnalyzers();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush([{
        id: 1,
        analyzerName: 'checkstyleAnalyzer',
        enabled: true
      }], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/analyzers',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.analyzers.length).toBe(1);
          expect(availableAnalyzerSpy).toHaveBeenCalled();
        });
      });
    });
  });

  it('should get project analyzer body length == 0', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush([], {
      status: 200,
      url: '/projects/1/modules',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      component.projectId = 1;
      const availableAnalyzerSpy = spyOn((ConfigureProjectComponent.prototype as any), 'getAvailableAnalyzers')
        .and.callFake(callback => {});
      (component as any).getProjectAnalyzers();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush([], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/analyzers',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.analyzers.length).toBe(0);
          expect(availableAnalyzerSpy).toHaveBeenCalled();
        });
      });
    });
  });

  it('should get project analyzer forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush([], {
      status: 200,
      url: '/projects/1/analyzers',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      component.projectId = 1;
      (component as any).getProjectAnalyzers();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects/1/analyzers'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects/1/analyzers',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(refreshSpy).toHaveBeenCalled();
        });
      });
    });
  }));

  it('should get available analyzers one analyzer', () => {
    expect(component.analyzers.length).toBe(0);
    (component as any).getAvailableAnalyzers();
    http.expectOne(`${AppComponent.getApiUrl()}analyzers`).flush(['checkstyleAnalyzer'], {
      status: 200,
      url: '/analyzers',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      expect(component.analyzers.length).toBe(1);
    });
  });

  it('should get available analyzers two analyzer', () => {
    expect(component.analyzers.length).toBe(0);
    (component as any).getAvailableAnalyzers();
    http.expectOne(`${AppComponent.getApiUrl()}analyzers`).flush(['checkstyleAnalyzer', 'todoAnalyzer'], {
      status: 200,
      url: '/analyzers',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      expect(component.analyzers.length).toBe(2);
    });
  });

  it('should get available analyzers analyzer already available', () => {
    component.analyzers.push({
      id: 1,
      analyzerName: 'checkstyleAnalyzer',
      enabled: true
    });
    expect(component.analyzers.length).toBe(1);
    (component as any).getAvailableAnalyzers();
    http.expectOne(`${AppComponent.getApiUrl()}analyzers`).flush(['checkstyleAnalyzer'], {
      status: 200,
      url: '/analyzers',
      statusText: 'Ok',
    });
    fixture.whenStable().then(() => {
      expect(component.analyzers.length).toBe(1);
      component.analyzers = [];
    });
  });

  it('should get modules for project forbidden', inject([UserService], (userService: UserService) => {
    const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
    });
    (component as any).getAvailableAnalyzers();
    http.expectOne(`${AppComponent.getApiUrl()}analyzers`).flush({
      status: 403,
      error: 'Forbidden',
      message: 'Access Denied',
      path: '/analyzers'
    }, {
      status: 403,
      statusText: 'Forbidden',
      url: '/analyzers',
    });
    fixture.whenStable().then(() => {
      fixture.whenStable().then(() => {
        expect(refreshSpy).toHaveBeenCalled();
      });
    });
  }));

  it('should get project file patterns', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/filePatterns',
    });
    fixture.whenStable().then(() => {
      (component as any).getProjectFilePatterns();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush([{
        id: 1,
        pattern: '**/*.java',
        inclusionType: 'INCLUDE'
      }], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/filePatterns',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.filePatterns.length).toBe(1);
        });
      });
    });
  });

  it('should get project file patterns body length == 0', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/filePatterns',
    });
    fixture.whenStable().then(() => {
      (component as any).getProjectFilePatterns();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush([], {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/filePatterns',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(component.filePatterns.length).toBe(0);
        });
      });
    });
  });

  it('should get project file patterns forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush([], {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/filePatterns',
    });
    fixture.whenStable().then(() => {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      (component as any).getProjectFilePatterns();
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects/1/filePatterns'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects/1/filePatterns'
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(refreshSpy).toHaveBeenCalled();
        });
      });
    });
  }));

  it('should get project name', inject([Title], (titleService: Title) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      const titleSpy = spyOn(titleService, 'setTitle').and.callFake(callback => {
      });
      (component as any).getProjectName();
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
          expect(component.projectName).toBe('test');
          expect(titleSpy).toHaveBeenCalledWith('Coderadar - Configure test');
        });
      });
    });
  }));

  it('should get project name forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1',
    });
    fixture.whenStable().then(() => {
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      (component as any).getProjectName();
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

  it('should delete module', () => {
    const module = {id: 1, path: '/src/main/java/testDir'};
    component.modules.push(module);
    expect(component.modules.length).toBe(1);
    (component as any).deleteModule(module);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/modules/1',
    });
    fixture.whenStable().then(() => {
      expect(component.modules.length).toBe(0);
    });
  });

  it('should delete module forbidden', inject([UserService], (userService: UserService) => {
    const module = {id: 1, path: '/src/main/java/testDir'};
    component.modules.push(module);
    expect(component.modules.length).toBe(1);
    const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
    });
    (component as any).deleteModule(module);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules/1`).flush({
      status: 403,
      error: 'Forbidden',
      message: 'Access Denied',
      path: '/projects/1/modules/1'
    }, {
      status: 403,
      statusText: 'Forbidden',
      url: '/projects/1/modules/1',
    });
    fixture.whenStable().then(() => {
      expect(refreshSpy).toHaveBeenCalled();
    });
  }));

  it('should delete module unprocessable entity', () => {
    const module = {id: 1, path: '/src/main/java/testDir'};
    component.modules.push(module);
    expect(component.modules.length).toBe(1);
    (component as any).deleteModule(module);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/modules/1`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      path: '/projects'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects',
    });
    fixture.whenStable().then(() => {
      expect(mockSnackbar.open).toHaveBeenCalledWith('Cannot edit the project! Try again later', '🞩', {duration: 4000});
    });
  });

  it('should submit analyzer configuration id', () => {
    const analyzerConfiguration = {
      id: 1,
      analyzerName: 'checkstyleAnalyzer',
      enabled: true
    };
    (component as any).submitAnalyzerConfiguration(analyzerConfiguration);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/analyzers/1',
    });
  });

  it('should submit analyzer configuration id forbidden', inject([UserService], (userService: UserService) => {
    const analyzerConfiguration = {
      id: 1,
      analyzerName: 'checkstyleAnalyzer',
      enabled: true
    };
    const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
    });
    (component as any).submitAnalyzerConfiguration(analyzerConfiguration);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers/1`).flush({
      status: 403,
      error: 'Forbidden',
      message: 'Access Denied',
      path: '/projects/1/analyzers/1'
    }, {
      status: 403,
      statusText: 'Forbidden',
      url: '/projects/1/analyzers/1',
    });
    fixture.whenStable().then(() => {
      expect(refreshSpy).toHaveBeenCalled();
    });
  }));

  it('should submit analyzer configuration id unprocessable entity', () => {
    const analyzerConfiguration = {
      id: 1,
      analyzerName: 'checkstyleAnalyzer',
      enabled: true
    };
    (component as any).submitAnalyzerConfiguration(analyzerConfiguration);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers/1`).flush({
      status: 422,
      error: 'Unprocessable Entity',
      path: '/projects/1/analyzers/1'
    }, {
      status: 422,
      statusText: 'Unprocessable Entity',
      url: '/projects/1/analyzers/1',
    });
    fixture.whenStable().then(() => {
      expect(mockSnackbar.open).toHaveBeenCalledWith('Cannot edit the project! Try again later', '🞩', {duration: 4000});
    });
  });

  it('should submit analyzer configuration no id', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/analyzers',
    });
    fixture.whenStable().then(() => {
      const analyzerConfiguration = {
        id: undefined,
        analyzerName: 'checkstyleAnalyzer',
        enabled: true
      };
      (component as any).submitAnalyzerConfiguration(analyzerConfiguration);
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush({id: 1}, {
        status: 200,
        statusText: 'Ok',
        url: '/projects/1/analyzers',
      });
    });
  });

  it('should submit analyzer configuration no id forbidden', inject([UserService], (userService: UserService) => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/analyzers',
    });
    fixture.whenStable().then(() => {
      const analyzerConfiguration = {
        id: undefined,
        analyzerName: 'checkstyleAnalyzer',
        enabled: true
      };
      const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
      });
      (component as any).submitAnalyzerConfiguration(analyzerConfiguration);
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush({
        status: 403,
        error: 'Forbidden',
        message: 'Access Denied',
        path: '/projects/1/analyzers'
      }, {
        status: 403,
        statusText: 'Forbidden',
        url: '/projects/1/analyzers',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(refreshSpy).toHaveBeenCalled();
        });
      });
    });
  }));

  it('should submit analyzer configuration no id unprocessable entity', () => {
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/analyzers',
    });
    fixture.whenStable().then(() => {
      const analyzerConfiguration = {
        id: undefined,
        analyzerName: 'checkstyleAnalyzer',
        enabled: true
      };
      (component as any).submitAnalyzerConfiguration(analyzerConfiguration);
      http.expectOne(`${AppComponent.getApiUrl()}projects/1/analyzers`).flush({
        status: 422,
        error: 'Unprocessable Entity',
        errorMessage: 'The project test already exists.',
        path: '/projects/1/analyzers'
      }, {
        status: 422,
        statusText: 'Unprocessable Entity',
        url: '/projects/1/analyzers',
      });
      fixture.whenStable().then(() => {
        fixture.whenStable().then(() => {
          expect(mockSnackbar.open).toHaveBeenCalledWith('Cannot edit the project! Try again later', '🞩', {duration: 4000});
        });
      });
    });
  });

  it('should delete file pattern', () => {
    const filePattern = {
      id: 1,
      pattern: '**/*.java',
      inclusionType: 'INCLUDE'
    };
    component.filePatterns.push(filePattern);
    expect(component.filePatterns.length).toBe(1);
    (component as any).deleteFilePattern(filePattern);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns/1`).flush({}, {
      status: 200,
      statusText: 'Ok',
      url: '/projects/1/filePatterns/1',
    });
    fixture.whenStable().then(() => {
      expect(component.filePatterns.length).toBe(0);
    });
  });

  it('should delete file pattern forbidden', inject([UserService], (userService: UserService) => {
    const filePattern = {
      id: 1,
      pattern: '**/*.java',
      inclusionType: 'INCLUDE'
    };
    component.filePatterns.push(filePattern);
    const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
    });
    expect(component.filePatterns.length).toBe(1);
    (component as any).deleteFilePattern(filePattern);
    http.expectOne(`${AppComponent.getApiUrl()}projects/1/filePatterns/1`).flush({
      status: 403,
      error: 'Forbidden',
      message: 'Access Denied',
      path: '/projects/1/filePatterns/1'
    }, {
      status: 403,
      statusText: 'Forbidden',
      url: '/projects/1/filePatterns/1',
    });
    fixture.whenStable().then(() => {
      expect(refreshSpy).toHaveBeenCalled();
    });
  }));
});

describe('ConfigureProjectComponent', () => {
  let fixture;
  let component;
  let submitAnalyzerSpy;
  let submitFilepatternSpy;
  let deleteFilpatternSpy;
  let submitModuleSpy;
  let deleteModuleSpy;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfigureProjectComponent],
      imports: [
        FormsModule, // ngModel
        RouterTestingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatCheckboxModule,
        HttpClientTestingModule
      ],
      providers: [
        {provide: MatSnackBar},
        {
          provide: ActivatedRoute, useValue: {
            params: of({id: 1})
          }
        }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ConfigureProjectComponent);
    component = fixture.componentInstance;
    submitAnalyzerSpy = spyOn(component, 'submitAnalyzerConfiguration').and.callFake(() => {});
    submitFilepatternSpy = spyOn(component, 'submitFilePattern').and.callFake(() => {});
    deleteFilpatternSpy = spyOn((component as any), 'deleteFilePattern').and.callThrough();
    submitModuleSpy = spyOn(component, 'submitModule').and.callFake(() => {});
    deleteModuleSpy = spyOn((component as any), 'deleteModule').and.callFake(() => {});
    fixture.detectChanges();
    return fixture.whenStable().then(() => {
      fixture.detectChanges();
    });
  });

  it('should render', () => {
    component.projectName = 'test';
    component.analyzers = [
      new AnalyzerConfiguration('analyzer.A', true),
      new AnalyzerConfiguration('analyzer.B', false),
    ];
    fixture.detectChanges();
    expect(component.projectName).toBe('test');
    const dom = fixture.debugElement.nativeElement;
    expect(dom.querySelector('mat-card-title > span').innerText).toBe('Configure test');
    const checkboxes = dom.querySelectorAll('mat-checkbox');
    expect(checkboxes.length).toBe(2);
    expect(checkboxes[0].innerText).toBe('A');
    expect(checkboxes[0].getAttribute('ng-reflect-model')).toBe('true');
    expect(checkboxes[1].innerText).toBe('B');
    expect(checkboxes[1].getAttribute('ng-reflect-model')).toBe('false');
  });

  it('should render analyzers', () => {
    component.analyzers = [
      {id: 1, analyzerName: 'analyzers.A', enabled: true},
      {id: 2, analyzerName: 'analyzers.B', enabled: false},
      {id: 3, analyzerName: 'analyzers.C', enabled: true},
      {id: 4, analyzerName: 'analyzers.D', enabled: true}
    ];
    fixture.detectChanges();
    const list = fixture.debugElement.queryAll(By.css('.list'))[0].nativeElement;
    expect(list.children.length).toBe(5);
    expect((list.children[1].children[0] as HTMLElement).getAttribute('ng-reflect-name')).toBe('analyzer-analyzers.A');
    expect((list.children[1].children[0] as HTMLElement).getAttribute('ng-reflect-model')).toBe('true');
    expect((list.children[2].children[0] as HTMLElement).getAttribute('ng-reflect-name')).toBe('analyzer-analyzers.B');
    expect((list.children[2].children[0] as HTMLElement).getAttribute('ng-reflect-model')).toBe('false');
    expect((list.children[3].children[0] as HTMLElement).getAttribute('ng-reflect-name')).toBe('analyzer-analyzers.C');
    expect((list.children[3].children[0] as HTMLElement).getAttribute('ng-reflect-model')).toBe('true');
    expect((list.children[4].children[0] as HTMLElement).getAttribute('ng-reflect-name')).toBe('analyzer-analyzers.D');
    expect((list.children[4].children[0] as HTMLElement).getAttribute('ng-reflect-model')).toBe('true');
  });

  it('should render file patterns', () => {
    component.filePatterns = [
      {id: 1, pattern: '**/*.java', inclusionType: 'INCLUDE'},
      {id: 2, pattern: '**/*.kt', inclusionType: 'INCLUDE'},
      {id: 3, pattern: 'src/main/test/**/*.java', inclusionType: 'EXCLUDE'},
      {id: 4, pattern: 'src/main/test/**/*.kt', inclusionType: 'EXCLUDE'},
    ];
    fixture.detectChanges();
    const includeList = fixture.debugElement.queryAll(By.css('.list'))[1].nativeElement;
    expect(includeList.children.length).toBe(6);
    expect(includeList.children[1].children[0].children[0].innerText).toBe('**/*.java');
    expect(includeList.children[2].children[0].children[0].innerText).toBe('**/*.kt');
    expect(includeList.children[3].children.length).toBe(0);
    expect(includeList.children[4].children.length).toBe(0);
    const excludeList = fixture.debugElement.queryAll(By.css('.list'))[2].nativeElement;
    expect(excludeList.children.length).toBe(6);
    expect(excludeList.children[1].children.length).toBe(0);
    expect(excludeList.children[2].children.length).toBe(0);
    expect(excludeList.children[3].children[0].children[0].innerText).toBe('src/main/test/**/*.java');
    expect(excludeList.children[4].children[0].children[0].innerText).toBe('src/main/test/**/*.kt');
  });

  it('should render modules', () => {
    component.modules = [
      {id: 1, path: 'coderadar/coderadar-plugins-api'},
      {id: 2, path: 'coderadar/coderadar-rest'},
      {id: 3, path: 'coderadar/coderadar-core'},
      {id: 4, path: 'coderadar/coderadar-graph'},
      {id: 5, path: 'coderadar/coderadar-plugins'}
    ];
    fixture.detectChanges();
    const excludeList = fixture.debugElement.queryAll(By.css('.list'))[3].nativeElement;
    expect(excludeList.children.length).toBe(7);
    expect(excludeList.children[1].children[0].innerText).toBe('coderadar/coderadar-plugins-api');
    expect(excludeList.children[2].children[0].innerText).toBe('coderadar/coderadar-rest');
    expect(excludeList.children[3].children[0].innerText).toBe('coderadar/coderadar-core');
    expect(excludeList.children[4].children[0].innerText).toBe('coderadar/coderadar-graph');
    expect(excludeList.children[5].children[0].innerText).toBe('coderadar/coderadar-plugins');
  });

  it('should listen on checkbox click in html', () => {
    component.projectName = 'test';
    component.analyzers = [
      new AnalyzerConfiguration('analyzer.A', true),
      new AnalyzerConfiguration('analyzer.B', false)
    ];
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      fixture.detectChanges();
      const analyzerList = fixture.debugElement.queryAll(By.css('.list'))[0].nativeElement;
      expect(analyzerList.children.length).toBe(3);
      const analyzerB = (analyzerList.children[2].children[0] as HTMLElement);
      const checkBox = analyzerB.querySelector('label');
      expect(analyzerB.getAttribute('ng-reflect-name')).toBe('analyzer-analyzer.B');
      expect(analyzerB.getAttribute('ng-reflect-model')).toBe('false');
      checkBox.click();
      fixture.detectChanges();
      expect(submitAnalyzerSpy).toHaveBeenCalled();
      expect(submitAnalyzerSpy).toHaveBeenCalledWith(component.analyzers[1]);
      fixture.detectChanges();
      expect(component.analyzers[1].enabled).toBeTruthy();
      expect(analyzerB.getAttribute('ng-reflect-name')).toBe('analyzer-analyzer.B');
      expect(analyzerB.getAttribute('ng-reflect-model')).toBe('true');
    });
  });

  it('should add file pattern to include in html', () => {
    const input = fixture.debugElement.query(By.css('input[name="includePatterns"]')).nativeElement;
    const button = fixture.debugElement.query(By.css('button[type="button"]')).nativeElement;
    input.value = '**/*.java';
    input.dispatchEvent(new Event('input'));
    expect(component.filePatternIncludeInput).toEqual('**/*.java');
    fixture.detectChanges();
    button.click();
    expect(submitFilepatternSpy).toHaveBeenCalledWith('INCLUDE');
    component.filePatterns.push({pattern: '**/*.java', inclusionType: 'INCLUDE'});
    fixture.detectChanges();
    const patternList = fixture.debugElement.queryAll(By.css('.list'))[1].nativeElement;
    expect(patternList.children[1].children[0].children[0].innerText).toBe('**/*.java');
  });

  it('should add file pattern to exclude in html', () => {
    const input = fixture.debugElement.query(By.css('input[name="excludePatterns"]')).nativeElement;
    const button = fixture.debugElement.queryAll(By.css('button[type="button"]'))[1].nativeElement;
    input.value = '**/*.java';
    input.dispatchEvent(new Event('input'));
    expect(component.filePatternExcludeInput).toEqual('**/*.java');
    fixture.detectChanges();
    button.click();
    expect(submitFilepatternSpy).toHaveBeenCalledWith('EXCLUDE');
    component.filePatterns.push({pattern: '**/*.java', inclusionType: 'EXCLUDE'});
    fixture.detectChanges();
    const patternList = fixture.debugElement.queryAll(By.css('.list'))[2].nativeElement;
    expect(patternList.children[1].children[0].children[0].innerText).toBe('**/*.java');
  });

  it('should delete file pattern in html', () => {
    component.filePatterns = [
      {id: 1, pattern: '**/*.java', inclusionType: 'INCLUDE'}
    ];
    fixture.detectChanges();
    const button = fixture.debugElement.queryAll(By.css('button[mat-button]'))[0].nativeElement;
    button.click();
    fixture.detectChanges();
    expect(deleteFilpatternSpy).toHaveBeenCalledWith({id: 1, pattern: '**/*.java', inclusionType: 'INCLUDE'});
    component.filePatterns = component.filePatterns.filter(value => value.pattern !== '**/*.java');
    fixture.detectChanges();
    expect(fixture.debugElement.queryAll(By.css('.list'))[1].nativeElement.children.length).toBe(2);
  });

  it('should add module in html', () => {
    const input = fixture.debugElement.query(By.css('input[name="modules"]')).nativeElement;
    const button = input.parentElement.nextElementSibling;
    input.value = 'coderadar/coderadar-plugin-api';
    input.dispatchEvent(new Event('input'));
    fixture.detectChanges();
    expect(component.modulesInput).toBe('coderadar/coderadar-plugin-api');
    button.click();
    expect(submitModuleSpy).toHaveBeenCalled();
    component.modules.push({id: 1, path: 'coderadar/coderadar-plugin-api'});
    fixture.detectChanges();
    const list = fixture.debugElement.queryAll(By.css('.list'))[3].nativeElement;
    expect(list.children.length).toBe(3);
    expect(list.children[1].children[0].innerText).toBe('coderadar/coderadar-plugin-api');
  });

  it('should delete module in html', () => {
    component.modules = [
      {id: 1, path: 'coderadar/coderadar-plugin-api'},
    ];
    fixture.detectChanges();
    const button = fixture.debugElement.queryAll(By.css('button[mat-button]'))[2].nativeElement;
    button.click();
    expect(deleteModuleSpy).toHaveBeenCalled();
    component.modules = component.modules.filter(module => module.path !== 'coderadar/coderadar-plugin-api');
    expect(component.modules.length).toBe(0);
    fixture.detectChanges();
    expect(fixture.debugElement.queryAll(By.css('.list'))[3].nativeElement.children.length).toBe(2);
  });
});
