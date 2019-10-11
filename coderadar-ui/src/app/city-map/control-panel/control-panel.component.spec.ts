import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ControlPanelComponent} from './control-panel.component';
import {CommitChooserComponent} from './commit-chooser/commit-chooser.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  MatAutocompleteModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatInputModule,
  MatRadioModule,
  MatSelectModule
} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SearchComponent} from './search/search.component';
import {SettingsComponent} from './settings/settings.component';
import {AutosuggestWrapperComponent} from '../autosuggest-wrapper/autosuggest-wrapper.component';
import {MetricMappingComponent} from './settings/metric-mapping/metric-mapping.component';
import {FilterComponent} from './settings/filter/filter.component';
import {ViewControlComponent} from './settings/view-control/view-control.component';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {Store} from '@ngrx/store';
import {FocusService} from '../service/focus.service';

describe('ControlPanelComponent', () => {
  let component: ControlPanelComponent;
  let fixture: ComponentFixture<ControlPanelComponent>;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MatInputModule, MatAutocompleteModule, MatFormFieldModule, FormsModule,
        BrowserAnimationsModule, MatSelectModule, MatCheckboxModule, MatRadioModule],
      declarations: [ControlPanelComponent, CommitChooserComponent, SearchComponent, SettingsComponent,
        AutosuggestWrapperComponent, MetricMappingComponent, FilterComponent, ViewControlComponent, FaIconComponent],
      providers: [
        {provide: Store},
        {provide: FocusService}
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    fixture.debugElement.injector.get('Store');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
