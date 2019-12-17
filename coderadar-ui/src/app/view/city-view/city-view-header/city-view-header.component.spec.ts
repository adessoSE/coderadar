import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CityViewHeaderComponent} from './city-view-header.component';
import {CUSTOM_ELEMENTS_SCHEMA, InjectionToken} from '@angular/core';
import {MatMenuModule} from '@angular/material/menu';
import {UserService} from '../../../service/user.service';
import {AppEffects} from '../../../city-map/shared/effects';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {
  ActionsSubject,
  ReducerManager,
  ReducerManagerDispatcher, ScannedActionsSubject,
  StateObservable,
  Store
} from '@ngrx/store';
import {Actions} from '@ngrx/effects';

describe('CityViewHeaderComponent', () => {
  let component: CityViewHeaderComponent;
  let fixture: ComponentFixture<CityViewHeaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CityViewHeaderComponent],
      imports: [
        MatMenuModule, // matMenuTriggerFor
        RouterTestingModule
      ],
      providers: [
        {provide: UserService},
        Store,
        {provide: AppEffects, useValue: {
            currentProjectId: 1
          }},
        ActionsSubject,
        Actions,
        ScannedActionsSubject,
        HttpClient,
        HttpHandler,
        {provide: ReducerManager},
        ReducerManagerDispatcher,
        {provide: InjectionToken},
        {provide: StateObservable},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CityViewHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
