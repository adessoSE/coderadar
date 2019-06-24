import {inject, TestBed} from '@angular/core/testing';

import {ComparisonPanelService} from './comparison-panel.service';

describe('ComparisonPanelService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ComparisonPanelService]
    });
  });

  it('should ...', inject([ComparisonPanelService], (service: ComparisonPanelService) => {
    expect(service).toBeTruthy();
  }));
});
