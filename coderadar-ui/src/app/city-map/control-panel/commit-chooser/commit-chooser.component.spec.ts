import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CommitChooserComponent} from './commit-chooser.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AutosuggestWrapperComponent} from '../../autosuggest-wrapper/autosuggest-wrapper.component';
import {HighlightSearchPipe} from "../pipes/highlight-search.pipe";
import {DatePipe} from "@angular/common";
import {of} from "rxjs";
import {Commit} from "../../../model/commit";

describe('CommitChooserComponent', () => {
  let component: CommitChooserComponent;
  let fixture: ComponentFixture<CommitChooserComponent>;
  const commit1 = new Commit({name: 'acb829dd3fd5bf77fa3a2e03dcc2e5ea', author: 'Foo', comment: 'initial commit', timestamp: 1580083200000, analyzed: true}); // 27.01.2020
  const commit2 = new Commit({name: 'b2d0f2c0ab312d30bb3053dea17a63f8', author: 'Foo', comment: 'second commit', timestamp: 1579996800000, analyzed: true}); // 26.01.2020
  const commit3 = new Commit({name: 'cbb88d98e53c7bb8f22514caeebca880', author: 'Foo', comment: 'third commit', timestamp: 1580169600000, analyzed: true}); // 28.01.2020
  const commit4 = new Commit({name: 'de846506bb40345c920d5d772c3c9f46', author: 'Bar', comment: 'fourth commit', timestamp: 1580256000000, analyzed: true}); // 29.01.2020
  const commit5 = new Commit({name: 'e929ef89c93ea4a16397453dfde84460', author: 'Bar', comment: 'fifth commit', timestamp: 1580342400000, analyzed: true}); // 30.01.2020
  const commit6 = new Commit({name: 'f8ddf8ace04646abad0e906e780bdcd3', author: 'Bar', comment: 'sixth commit', timestamp: 1580428800000, analyzed: true}); // 31.01.2020
  let source;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MatAutocompleteModule, MatFormFieldModule, FormsModule, MatInputModule,
        BrowserAnimationsModule],
      providers: [
        DatePipe
      ],
      declarations: [CommitChooserComponent, AutosuggestWrapperComponent, HighlightSearchPipe]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CommitChooserComponent);
    component = fixture.componentInstance;
    component.selected = of(commit1);
    source = [
      {value: commit1, displayValue: component.formatCommit(commit1)},
      {value: commit2, displayValue: component.formatCommit(commit2)},
      {value: commit3, displayValue: component.formatCommit(commit3)},
      {value: commit4, displayValue: component.formatCommit(commit4)},
      {value: commit5, displayValue: component.formatCommit(commit5)},
      {value: commit6, displayValue: component.formatCommit(commit6)}
    ];
    component.commits = of([commit1, commit2, commit3, commit4, commit5, commit6]);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should format commit', () => {
    expect(component.formatCommit(commit1)).toBe('acb829d, Foo, Mon, 27 Jan 2020 00:00:00 GMT');
    expect(component.formatCommit(undefined)).toBe('empty');
    expect(component.formatCommit(null)).toBe('empty');
  });

  it('should format commit short name', () => {
    expect(component.formatCommit(new Commit({name: 'dssd', author: 'Foo', timestamp: 1580083200000}))).toBe('dssd, Foo, Mon, 27 Jan 2020 00:00:00 GMT');
  });

  it('should format commit no name', () => {
    expect(() => component.formatCommit(new Commit({author: 'Foo', timestamp: 1580083200000}))).toThrow();
    expect(() => component.formatCommit(new Commit({author: 'Foo', timestamp: 1580083200000}))).toThrowError();
    expect(() => component.formatCommit(new Commit({author: 'Foo', timestamp: 1580083200000}))).toThrowError('commit.name is undefined');
  });

  it('should format commit no author', () => {
    expect(component.formatCommit(new Commit({name: 'acb829d', timestamp: 1580083200000}))).toBe('acb829d, undefined, Mon, 27 Jan 2020 00:00:00 GMT');
  });

  it('should format commit no timestamp', () => {
    expect(component.formatCommit(new Commit({name: 'acb829d', author: 'Foo',}))).toBe('acb829d, Foo, Invalid Date');
  });

  it('should filter commits source undefined', () => {
    expect(component.filterCommitOptions('test', undefined).length).toBe(0);
    expect(component.filterCommitOptions('test', []).length).toBe(0);
    expect(component.filterCommitOptions(undefined, undefined).length).toBe(0);
    expect(component.filterCommitOptions(undefined, source).length).toBe(0);
  });

  it('should filter commits empty value', () => {
    const filtered = component.filterCommitOptions('', source);
    expect(filtered.length).toBe(6);
    expect(filtered[0].value.name).toBe('acb829dd3fd5bf77fa3a2e03dcc2e5ea');
    expect(filtered[1].value.name).toBe('b2d0f2c0ab312d30bb3053dea17a63f8');
    expect(filtered[2].value.name).toBe('cbb88d98e53c7bb8f22514caeebca880');
    expect(filtered[3].value.name).toBe('de846506bb40345c920d5d772c3c9f46');
    expect(filtered[4].value.name).toBe('e929ef89c93ea4a16397453dfde84460');
    expect(filtered[5].value.name).toBe('f8ddf8ace04646abad0e906e780bdcd3');
  });

  it('should filter commits author Bar', () => {
    const filtered = component.filterCommitOptions('Bar', source);
    expect(filtered.length).toBe(3);
    expect(filtered[0].value.name).toBe('de846506bb40345c920d5d772c3c9f46');
    expect(filtered[1].value.name).toBe('e929ef89c93ea4a16397453dfde84460');
    expect(filtered[2].value.name).toBe('f8ddf8ace04646abad0e906e780bdcd3');
  });

  it('should filter commits name de84', () => {
    const filtered = component.filterCommitOptions('de84', source);
    expect(filtered.length).toBe(2);
    expect(filtered[0].value.name).toBe('de846506bb40345c920d5d772c3c9f46');
    expect(filtered[1].value.name).toBe('e929ef89c93ea4a16397453dfde84460');
  });

  it('should filter commits string not included', () => {
    const filtered = component.filterCommitOptions('hello', source);
    expect(filtered.length).toBe(0);
  });
});
