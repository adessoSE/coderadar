import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewControlComponent} from './view-control.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

describe('ViewControlComponent', () => {
  let component: ViewControlComponent;
  let fixture: ComponentFixture<ViewControlComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewControlComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ViewControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
