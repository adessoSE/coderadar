import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ComparisonPanelComponent} from './comparison-panel.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {Store} from '@ngrx/store';
import {ComparisonPanelService} from '../../service/comparison-panel.service';
import {VisualizationModule} from '../visualization.module';

describe('ComparisonPanelComponent', () => {
  let component: ComparisonPanelComponent;
  let fixture: ComponentFixture<ComparisonPanelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        VisualizationModule
      ],
      providers: [
        ComparisonPanelService,
        {provide: Store}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ComparisonPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
