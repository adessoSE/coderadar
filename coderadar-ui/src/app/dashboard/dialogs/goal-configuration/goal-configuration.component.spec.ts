import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GoalConfigurationComponent } from './goal-configuration.component';

describe('GoalConfigurationComponent', () => {
  let component: GoalConfigurationComponent;
  let fixture: ComponentFixture<GoalConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GoalConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GoalConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
