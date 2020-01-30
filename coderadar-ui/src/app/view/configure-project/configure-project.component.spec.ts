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
import {HttpClientModule} from '@angular/common/http';
import {Title} from "@angular/platform-browser";

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
      const availableAnalyzerSpy = spyOn((ConfigureProjectComponent.prototype as any), 'getAvailableAnalyzers').and.callFake(callback => {});
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
      const availableAnalyzerSpy = spyOn((ConfigureProjectComponent.prototype as any), 'getAvailableAnalyzers').and.callFake(callback => {});
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
    })
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
    })
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
    })
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
    component.filePatterns.push(filePattern);const refreshSpy = spyOn(userService, 'refresh').and.callFake(callback => {
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
