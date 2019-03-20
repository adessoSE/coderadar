import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AutosuggestWrapperComponent} from './autosuggest-wrapper.component';

describe('AutosuggestWrapperComponent', () => {
    let component: AutosuggestWrapperComponent;
    let fixture: ComponentFixture<AutosuggestWrapperComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AutosuggestWrapperComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AutosuggestWrapperComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
