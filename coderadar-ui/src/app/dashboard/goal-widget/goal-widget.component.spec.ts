import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GoalWidgetComponent } from './goal-widget.component';

describe('GoalWidgetComponent', () => {
  let component: GoalWidgetComponent;
  let fixture: ComponentFixture<GoalWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GoalWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GoalWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
