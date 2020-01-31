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

  it('should format value', () => {
    expect(component.formatValue(null)).toBe('');
    expect(component.formatValue(undefined)).toBe('');
    expect(component.formatValue('')).toBe('');
    expect(component.formatValue('test')).toBe('test');
  });

  it('should format value', () => {
    const value = {
      timestamp: '2020-01-01T08:00:00.000Z',
      name: 'test',
      author: 'testAuthor'
    };
    expect(component.formatValue(value)).toBe('Wed, 01 Jan 2020 08:00:00 GMT,  test, testAuthor');
  });

  it('should format value has only property name', () => {
    const value = {name: 'test'};
    // TODO 2 whitespaces?
    expect(component.formatValue(value)).toBe('Invalid Date,  test, undefined');
  });

  it('should format value has property not name', () => {
    const value = {notName: 'test'};
    expect(component.formatValue(value)).toEqual({notName: 'test'});
  });

  // TODO
  //  _filter
  //  _filter source undefined
  //  _filter value undefined
  //  _filter value has property name
  //  _filter value has property not name
  //  _filter value has no property


});
