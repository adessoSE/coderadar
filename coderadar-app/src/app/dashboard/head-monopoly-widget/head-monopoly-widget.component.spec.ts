import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeadMonopolyWidgetComponent } from './head-monopoly-widget.component';

describe('HeadMonopolyWidgetComponent', () => {
  let component: HeadMonopolyWidgetComponent;
  let fixture: ComponentFixture<HeadMonopolyWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeadMonopolyWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeadMonopolyWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
