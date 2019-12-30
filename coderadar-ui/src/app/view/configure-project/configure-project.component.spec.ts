import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ConfigureProjectComponent} from './configure-project.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserService} from '../../service/user.service';
import {RouterTestingModule} from '@angular/router/testing';
import {of} from 'rxjs';
import {HttpClient, HttpHandler, HttpResponse} from '@angular/common/http';
import {FilePattern} from '../../model/file-pattern';
import {ProjectService} from '../../service/project.service';

describe('ConfigureProjectComponent', () => {
  let component: ConfigureProjectComponent;
  let fixture: ComponentFixture<ConfigureProjectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfigureProjectComponent],
      imports: [
        FormsModule, // ngModel
        RouterTestingModule
      ],
      providers: [
        {provide: MatSnackBar},
        {provide: ActivatedRoute, useValue: {
            params: of({ id: 1 })
          }
        },
        {provide: UserService, useClass: MockUserService},
        {provide: ProjectService, useClass: MockProjectService},
        HttpClient,
        HttpHandler
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ConfigureProjectComponent);
    component = fixture.componentInstance;
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
    fixture.whenStable().then(() => {
      expect(component.modules.length).toBe(1);
      expect(component.modules[0].path).toBe('/src/main/java/testDir');
      expect(component.modules[0].id).toBe(1);
    });
  });

  it('should add file pattern', () => {
    expect(component.filePatterns.length).toBe(0);
    component.filePatternIncludeInput = '**/*.java';
    component.projectId = 1;
    component.submitFilePattern('INCLUDE');
    fixture.whenStable().then(() => {
      expect(component.filePatterns.length).toBe(1);
      expect(component.filePatterns[0].pattern).toBe('**/*.java');
      expect(component.filePatterns[0].id).toBe(1);
    });
  });
});

class MockUserService extends UserService {
  refresh(callback: () => any) {
    callback();
  }
}

class MockProjectService extends ProjectService {
  addProjectFilePattern(projectId: number, pattern: FilePattern) {
    return of(new HttpResponse({
      body: {
        id: 1
      }
    })).toPromise();
  }

  addProjectModule() {
    return of(new HttpResponse({
      body: {
        id: 1
      }
    })).toPromise();
  }
}
