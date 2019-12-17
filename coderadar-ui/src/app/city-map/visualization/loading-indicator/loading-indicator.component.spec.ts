import {ComponentFixture, TestBed} from '@angular/core/testing';

import {LoadingIndicatorComponent} from './loading-indicator.component';

describe('LoadingIndicatorComponent', () => {
  let component: LoadingIndicatorComponent;
  let fixture: ComponentFixture<LoadingIndicatorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoadingIndicatorComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(LoadingIndicatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
