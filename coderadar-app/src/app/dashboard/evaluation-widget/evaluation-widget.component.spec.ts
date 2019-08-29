import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationWidgetComponent } from './evaluation-widget.component';

describe('EvaluationWidgetComponent', () => {
  let component: EvaluationWidgetComponent;
  let fixture: ComponentFixture<EvaluationWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvaluationWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluationWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
