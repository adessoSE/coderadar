import {TestBed, inject} from '@angular/core/testing';

import {FocusService} from './focus.service';

describe('FocusService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [FocusService]
        });
    });

    it('should ...', inject([FocusService], (service: FocusService) => {
        expect(service).toBeTruthy();
    }));
});
