import {LayoutModule} from '@angular/cdk/layout';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MatButtonModule, MatCardModule, MatGridListModule, MatIconModule, MatMenuModule, } from '@angular/material';

import {MainDashboardComponent} from './main-dashboard.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Title} from '@angular/platform-browser';
import {UserService} from '../../service/user.service';
import {RouterTestingModule} from '@angular/router/testing';
import {
  ActionsSubject,
  ReducerManager,
  ReducerManagerDispatcher,
  ScannedActionsSubject,
  Store
} from '@ngrx/store';
import {AppEffects} from '../../city-map/shared/effects';
import {Actions} from '@ngrx/effects';
import {HttpClient, HttpHandler} from '@angular/common/http';

describe('MainDashboardComponent', () => {
  let component: MainDashboardComponent;
  let fixture: ComponentFixture<MainDashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainDashboardComponent],
      imports: [
        NoopAnimationsModule,
        LayoutModule,
        MatButtonModule,
        MatCardModule,
        MatGridListModule,
        MatIconModule,
        MatMenuModule,
        RouterTestingModule
      ],
      providers: [
        {provide: MatSnackBar},
        {provide: UserService},
        Store,
        AppEffects,
        ActionsSubject,
        Actions,
        ScannedActionsSubject,
        HttpClient,
        HttpHandler,
        {provide: ReducerManager},
        ReducerManagerDispatcher,
        {provide: ActivatedRoute},
        Title
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(MainDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
