import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PeriodWidgetComponent } from './period-widget.component';

describe('PeriodWidgetComponent', () => {
  let component: PeriodWidgetComponent;
  let fixture: ComponentFixture<PeriodWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PeriodWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PeriodWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
