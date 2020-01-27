import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchitectureWidgetComponent } from './architecture-widget.component';

describe('ArchitectureWidgetComponent', () => {
  let component: ArchitectureWidgetComponent;
  let fixture: ComponentFixture<ArchitectureWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArchitectureWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArchitectureWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
