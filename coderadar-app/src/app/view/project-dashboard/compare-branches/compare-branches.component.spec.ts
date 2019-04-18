import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompareBranchesComponent } from './compare-branches.component';

describe('CompareBranchesComponent', () => {
  let component: CompareBranchesComponent;
  let fixture: ComponentFixture<CompareBranchesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompareBranchesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompareBranchesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
