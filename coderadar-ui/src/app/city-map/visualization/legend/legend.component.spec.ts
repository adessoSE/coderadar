import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LegendComponent} from './legend.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActionsSubject, ReducerManager, StateObservable, Store} from '@ngrx/store';

describe('LegendComponent', () => {
  let component: LegendComponent;
  let fixture: ComponentFixture<LegendComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LegendComponent],
      providers: [
        Store,
        {provide: StateObservable},
        {provide: ReducerManager},
        {provide: ActionsSubject}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(LegendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
