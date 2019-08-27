import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueWidgetComponent } from './issue-widget.component';

describe('IssueWidgetComponent', () => {
  let component: IssueWidgetComponent;
  let fixture: ComponentFixture<IssueWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IssueWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
