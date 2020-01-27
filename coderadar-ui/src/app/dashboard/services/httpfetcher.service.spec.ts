import { TestBed } from '@angular/core/testing';

import { HttpfetcherService } from './httpfetcher.service';

describe('HttpfetcherService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpfetcherService = TestBed.get(HttpfetcherService);
    expect(service).toBeTruthy();
  });
});
