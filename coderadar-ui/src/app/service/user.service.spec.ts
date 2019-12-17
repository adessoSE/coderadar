import {TestBed} from '@angular/core/testing';

import {UserService} from './user.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

describe('UserService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      {provide: HttpClient},
      {provide: Router}
    ]
  }));

  it('should be created', () => {
    const service: UserService = TestBed.get(UserService);
    expect(service).toBeTruthy();
  });
});
