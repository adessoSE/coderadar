import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AutosuggestWrapperComponent} from './autosuggest-wrapper.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HighlightSearchPipe} from "../control-panel/pipes/highlight-search.pipe";
import {CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {of} from "rxjs";
import {DatePipe} from "@angular/common";

describe('AutosuggestWrapperComponent', () => {
  let component: AutosuggestWrapperComponent;
  let fixture: ComponentFixture<AutosuggestWrapperComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MatInputModule, MatAutocompleteModule, MatFormFieldModule, FormsModule,
        BrowserAnimationsModule],
      declarations: [AutosuggestWrapperComponent, HighlightSearchPipe],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AutosuggestWrapperComponent);
    component = fixture.componentInstance;
    component.source$ = of(['item a', 'item b', 'item c', 'item d']);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display function', () => {
    expect(component.displayFunction({
      value: 'test',
      displayValue: 'displayTest'
    })).toBe('displayTest');
  });

  it('should display function no option', () => {
    expect(component.displayFunction(undefined)).toBe('');
  });
});
