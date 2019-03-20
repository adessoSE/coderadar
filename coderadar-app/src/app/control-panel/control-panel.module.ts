import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ControlPanelComponent} from './control-panel.component';
import {SettingsComponent} from './settings/settings.component';
import {ViewControlComponent} from './settings/view-control/view-control.component';
import {FormsModule} from '@angular/forms';
import {NguiAutoCompleteModule} from '@ngui/auto-complete';
import {CommitChooserComponent} from './commit-chooser/commit-chooser.component';
import {CommitService} from '../service/commit.service';
import {SearchComponent} from './search/search.component';
import {FilterComponent} from './settings/filter/filter.component';
import {MetricMappingComponent} from './settings/metric-mapping/metric-mapping.component';
import {ScreenshotComponent} from './screenshot/screenshot.component';
import {AutosuggestWrapperComponent} from '../autosuggest-wrapper/autosuggest-wrapper.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NguiAutoCompleteModule,
        FontAwesomeModule
    ],
    declarations: [
        ControlPanelComponent,
        SettingsComponent,
        ViewControlComponent,
        CommitChooserComponent,
        SearchComponent,
        FilterComponent,
        MetricMappingComponent,
        ScreenshotComponent,
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
