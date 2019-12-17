import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TooltipComponent} from './tooltip.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {TooltipService} from '../../service/tooltip.service';
import {of} from 'rxjs';

describe('TooltipComponent', () => {
  let component: TooltipComponent;
  let fixture: ComponentFixture<TooltipComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TooltipComponent],
      providers: [
        {provide: TooltipService, useValue: {
            tooltipContent$: of({
              elementName: 'test',
              metrics: [{
                key: 'key',
                value: 'value'
              }]
            }),
            showTooltip$: of({}),
            hideTooltip$: of({}),
            trackPosition$: of({})
        }}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(TooltipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
