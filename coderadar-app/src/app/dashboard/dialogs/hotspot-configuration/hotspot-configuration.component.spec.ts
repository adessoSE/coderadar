import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HotspotConfigurationComponent } from './hotspot-configuration.component';

describe('HotspotConfigurationComponent', () => {
  let component: HotspotConfigurationComponent;
  let fixture: ComponentFixture<HotspotConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HotspotConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HotspotConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
