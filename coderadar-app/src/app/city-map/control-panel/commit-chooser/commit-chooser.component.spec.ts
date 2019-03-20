import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CommitChooserComponent} from './commit-chooser.component';

describe('CommitChooserComponent', () => {
    let component: CommitChooserComponent;
    let fixture: ComponentFixture<CommitChooserComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CommitChooserComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CommitChooserComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
