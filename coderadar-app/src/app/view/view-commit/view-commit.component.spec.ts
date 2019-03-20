import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewCommitComponent} from './view-commit.component';

describe('ViewCommitComponent', () => {
  let component: ViewCommitComponent;
  let fixture: ComponentFixture<ViewCommitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewCommitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewCommitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
