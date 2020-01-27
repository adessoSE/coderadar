import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PeriodConfigurationComponent } from './period-configuration.component';

describe('PeriodConfigurationComponent', () => {
  let component: PeriodConfigurationComponent;
  let fixture: ComponentFixture<PeriodConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PeriodConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PeriodConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
