import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MetricMappingComponent} from './metric-mapping.component';
import {Component, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {IMetricMapping} from '../../../interfaces/IMetricMapping';

describe('MetricMappingComponent', () => {
  let component: MetricMappingComponent;
  let fixture: ComponentFixture<TestComponentWrapperComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        MetricMappingComponent,
        TestComponentWrapperComponent
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(TestComponentWrapperComponent);
    component = fixture.debugElement.children[0].componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

@Component({
  selector: 'app-test-component-wrapper',
  template: '<app-metric-mapping [metricMapping]="metricMapping"></app-metric-mapping>'
})
class TestComponentWrapperComponent {
  metricMapping: IMetricMapping = {
    heightMetricName: '',
    groundAreaMetricName: '',
    colorMetricName: '',
  };
}
