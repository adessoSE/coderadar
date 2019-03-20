import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ComparisonPanelComponent} from './comparison-panel.component';

describe('ComparisonPanelComponent', () => {
    let component: ComparisonPanelComponent;
    let fixture: ComponentFixture<ComparisonPanelComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ComparisonPanelComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ComparisonPanelComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
