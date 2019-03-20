import {TestBed, inject} from '@angular/core/testing';

import {ScreenShotService} from './screenshot.service';

describe('ScreenShotService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [ScreenShotService]
        });
    });

    it('should ...', inject([ScreenShotService], (service: ScreenShotService) => {
        expect(service).toBeTruthy();
    }));
});
