import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContributorMergeDialogComponent } from './merge-dialog.component';

describe('MergeDialogComponent', () => {
  let component: ContributorMergeDialogComponent;
  let fixture: ComponentFixture<ContributorMergeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContributorMergeDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContributorMergeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
