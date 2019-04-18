import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitListComponent } from './commit-list.component';

describe('CommitListComponent', () => {
  let component: CommitListComponent;
  let fixture: ComponentFixture<CommitListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommitListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommitListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
