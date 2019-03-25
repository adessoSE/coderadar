import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ControlPanelComponent} from './control-panel.component';
import {SettingsComponent} from './settings/settings.component';
import {ViewControlComponent} from './settings/view-control/view-control.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NguiAutoCompleteModule} from '@ngui/auto-complete';
import {CommitChooserComponent} from './commit-chooser/commit-chooser.component';
import {CommitService} from '../service/commit.service';
import {SearchComponent} from './search/search.component';
import {FilterComponent} from './settings/filter/filter.component';
import {MetricMappingComponent} from './settings/metric-mapping/metric-mapping.component';
import {AutosuggestWrapperComponent} from '../autosuggest-wrapper/autosuggest-wrapper.component';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatSelectModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule, MatRadioModule,
  MatSidenavModule,
  MatToolbarModule,
} from '@angular/material';
import {LayoutModule} from '@angular/cdk/layout';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      NguiAutoCompleteModule,
      FontAwesomeModule,
      MatFormFieldModule,
      MatInputModule,
      MatRadioModule,
      MatCardModule,
      MatButtonModule,
      ReactiveFormsModule,
      MatGridListModule,
      MatMenuModule,
      MatListModule,
      MatIconModule,
      LayoutModule,
      MatToolbarModule,
      MatSidenavModule,
      MatSelectModule,
      MatCheckboxModule
    ],
    declarations: [
        ControlPanelComponent,
        SettingsComponent,
        ViewControlComponent,
        CommitChooserComponent,
        SearchComponent,
        FilterComponent,
        MetricMappingComponent,
        AutosuggestWrapperComponent
    ],
    exports: [
        ControlPanelComponent
    ],
    providers: [
        CommitService
    ]
})
export class ControlPanelModule {
}
