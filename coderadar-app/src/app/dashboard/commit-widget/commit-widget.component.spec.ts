import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitWidgetComponent } from './commit-widget.component';

describe('CommitWidgetComponent', () => {
  let component: CommitWidgetComponent;
  let fixture: ComponentFixture<CommitWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommitWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommitWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
