import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CityViewHeaderComponent } from './city-view-header.component';

describe('CityViewHeaderComponent', () => {
  let component: CityViewHeaderComponent;
  let fixture: ComponentFixture<CityViewHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CityViewHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CityViewHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
