import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoverageWidgetComponent } from './coverage-widget.component';

describe('CoverageWidgetComponent', () => {
  let component: CoverageWidgetComponent;
  let fixture: ComponentFixture<CoverageWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoverageWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoverageWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
