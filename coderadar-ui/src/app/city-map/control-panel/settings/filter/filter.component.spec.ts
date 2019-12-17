import {ComponentFixture, TestBed} from '@angular/core/testing';

import {FilterComponent} from './filter.component';
import {Component, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActionsSubject, ReducerManager, StateObservable, Store} from '@ngrx/store';
import {IFilter} from '../../../interfaces/IFilter';

describe('FilterComponent', () => {
  let component: FilterComponent;
  let fixture: ComponentFixture<TestComponentWrapperComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        TestComponentWrapperComponent,
        FilterComponent
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      providers: [
        {provide: Store},
        {provide: StateObservable},
        {provide: ReducerManager},
        {provide: ActionsSubject}
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(TestComponentWrapperComponent);
    component = fixture.debugElement.children[0].componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

@Component({
  selector: 'app-test-component-wrapper',
  template: '<app-filter [activeFilter]="activeFilter"></app-filter>'
})
class TestComponentWrapperComponent {
  activeFilter: IFilter = {
    unmodified: false,
    modified: false,
    deleted: false,
    added: false,
    renamed: false,
  };
}
