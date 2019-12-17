import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AutosuggestWrapperComponent} from './autosuggest-wrapper.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

describe('AutosuggestWrapperComponent', () => {
  let component: AutosuggestWrapperComponent;
  let fixture: ComponentFixture<AutosuggestWrapperComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MatInputModule, MatAutocompleteModule, MatFormFieldModule, FormsModule,
        BrowserAnimationsModule],
      declarations: [AutosuggestWrapperComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AutosuggestWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
