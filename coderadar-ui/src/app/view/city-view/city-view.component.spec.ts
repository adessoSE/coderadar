import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CityViewComponent} from './city-view.component';
import {CUSTOM_ELEMENTS_SCHEMA, InjectionToken} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {AppEffects} from '../../city-map/shared/effects';
import {UserService} from '../../service/user.service';
import {Title} from '@angular/platform-browser';
import {of} from 'rxjs';
import {Actions} from '@ngrx/effects';
import {
  ActionsSubject, ReducerManager,
  ReducerManagerDispatcher,
  ScannedActionsSubject,
  StateObservable,
  Store
} from '@ngrx/store';

describe('CityViewComponent', () => {
  let component: CityViewComponent;
  let fixture: ComponentFixture<CityViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CityViewComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      providers: [
        {provide: Router},
        {provide: ActivatedRoute, useValue: {
            params: of({ id: 1 })
          }
        },
        {provide: AppEffects, useValue: {
            currentProjectId: 1
          }},
        Actions,
        Store,
        ActionsSubject,
        ReducerManagerDispatcher,
        ReducerManager,
        {provide: InjectionToken},
        {provide: StateObservable},
        ScannedActionsSubject,
        {provide: UserService},
        {provide: Title},
        {provide: HttpClient}
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CityViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
