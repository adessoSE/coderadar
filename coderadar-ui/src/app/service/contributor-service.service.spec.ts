import { TestBed } from '@angular/core/testing';

import { ContributorService } from './contributor.service';

describe('ContributorServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ContributorService = TestBed.get(ContributorService);
    expect(service).toBeTruthy();
  });
});
