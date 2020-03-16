import {inject, TestBed} from '@angular/core/testing';

import {TooltipService} from './tooltip.service';

describe('TooltipService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TooltipService]
    });
  });

  it('should', inject([TooltipService], (service: TooltipService) => {
    expect(service).toBeTruthy();
  }));
});
