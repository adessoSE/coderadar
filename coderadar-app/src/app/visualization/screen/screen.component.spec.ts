import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ScreenComponent} from './screen.component';

describe('ScreenComponent', () => {
    let component: ScreenComponent;
    let fixture: ComponentFixture<ScreenComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ScreenComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ScreenComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
