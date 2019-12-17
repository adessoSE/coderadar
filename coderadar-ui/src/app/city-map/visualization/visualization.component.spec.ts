import {ComponentFixture, TestBed} from '@angular/core/testing';

import {VisualizationComponent} from './visualization.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActionsSubject, ReducerManager, StateObservable, Store} from '@ngrx/store';
import {ComparisonPanelService} from '../service/comparison-panel.service';

describe('VisualizationComponent', () => {
  let component: VisualizationComponent;
  let fixture: ComponentFixture<VisualizationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisualizationComponent],
      providers: [
        Store,
        ComparisonPanelService,
        {provide: StateObservable},
        {provide: ReducerManager},
        {provide: ActionsSubject}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(VisualizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
