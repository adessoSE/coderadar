import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditProjectComponent} from './edit-project.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {HttpClient, HttpHandler, HttpResponse} from '@angular/common/http';
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

describe('EditProjectComponent', () => {
  let component: EditProjectComponent;
  let fixture: ComponentFixture<EditProjectComponent>;
  let routerSpy;

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
        RouterTestingModule.withRoutes([
          {path: 'dashboard', component: MainDashboardComponent},
        ]),
      ],
      providers: [
        {provide: UserService, useClass: MockUserService},
        {provide: ProjectService, useClass: MockProjectService},
        HttpClient,
        HttpHandler,
        {provide: ActivatedRoute, useValue: {
            params: of({ id: 1 })
          }
        },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EditProjectComponent);
    component = fixture.componentInstance;
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake((url) => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should edit project', () => {
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
    component.submitForm();
    fixture.whenStable().then(() => {
      expect(routerSpy).toHaveBeenCalledWith(['/dashboard']);
      expect(component.incorrectURL).toBeFalsy();
      expect(component.projectExists).toBeFalsy();
    });
  });
});

class MockUserService extends UserService {
  refresh(callback: () => any) {
    callback();
  }
}

class MockProjectService extends ProjectService {
  editProject(project: Project) {
    return of(new HttpResponse({
      status: 200
    })).toPromise();
  }
}
