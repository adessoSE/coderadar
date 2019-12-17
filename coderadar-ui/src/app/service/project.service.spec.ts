import {TestBed} from '@angular/core/testing';

import {ProjectService} from './project.service';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {UserService} from './user.service';

describe('ProjectService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      {provide: Router},
      {provide: HttpClient},
      {provide: UserService}
    ]
  }));

  it('should be created', () => {
    const service: ProjectService = TestBed.get(ProjectService);
    expect(service).toBeTruthy();
  });
});
