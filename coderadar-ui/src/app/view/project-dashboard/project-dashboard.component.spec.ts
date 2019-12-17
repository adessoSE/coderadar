import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProjectDashboardComponent} from './project-dashboard.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {Title} from '@angular/platform-browser';
import {ScannedActionsSubject, Store} from '@ngrx/store';
import {AppEffects} from '../../city-map/shared/effects';
import {of} from 'rxjs';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {Actions} from '@ngrx/effects';

describe('ProjectDashboardComponent', () => {
  let component: ProjectDashboardComponent;
  let fixture: ComponentFixture<ProjectDashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectDashboardComponent],
      imports: [
        RouterTestingModule // ngModel
      ],
      providers: [
        {provide: Router},
        {provide: UserService},
        {provide: Title},
        {
          provide: ActivatedRoute, useValue: {
            params: of({id: 1})
          }
        },
        HttpClient,
        HttpHandler,
        {provide: Store},
        AppEffects,
        Actions,
        ScannedActionsSubject
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ProjectDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
