import { TestBed } from '@angular/core/testing';

import { ScreenInteractionService } from './screen-interaction.service';

describe('ScreenInteractionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ScreenInteractionService = TestBed.get(ScreenInteractionService);
    expect(service).toBeTruthy();
  });
});
