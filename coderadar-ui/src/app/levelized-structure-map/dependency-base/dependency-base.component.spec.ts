import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DependencyBaseComponent } from './dependency-base.component';

describe('DependencyBaseComponent', () => {
  let component: DependencyBaseComponent;
  let fixture: ComponentFixture<DependencyBaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DependencyBaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DependencyBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
