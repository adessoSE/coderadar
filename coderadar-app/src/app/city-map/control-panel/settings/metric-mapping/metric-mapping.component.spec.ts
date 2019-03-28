import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MetricMappingComponent} from './metric-mapping.component';

describe('MetricMappingComponent', () => {
  let component: MetricMappingComponent;
  let fixture: ComponentFixture<MetricMappingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MetricMappingComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MetricMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
