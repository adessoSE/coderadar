import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DependencyCompareComponent } from './dependency-compare.component';

describe('DependencyCompareComponent', () => {
  let component: DependencyCompareComponent;
  let fixture: ComponentFixture<DependencyCompareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DependencyCompareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DependencyCompareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
