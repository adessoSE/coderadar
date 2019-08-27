import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HotspotWidgetComponent } from './hotspot-widget.component';

describe('HotspotWidgetComponent', () => {
  let component: HotspotWidgetComponent;
  let fixture: ComponentFixture<HotspotWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HotspotWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HotspotWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
