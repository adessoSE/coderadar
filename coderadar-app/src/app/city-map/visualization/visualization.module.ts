import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {VisualizationComponent} from './visualization.component';
import {ScreenComponent} from './screen/screen.component';
import {MetricService} from '../../service/metric.service';
import {TooltipComponent} from './tooltip/tooltip.component';
import {ComparisonPanelComponent} from './comparison-panel/comparison-panel.component';
import {LegendComponent} from './legend/legend.component';
import {KeyValuePipe} from '../pipes/key-value.pipe';
import {LoadingIndicatorComponent} from './loading-indicator/loading-indicator.component';

@NgModule({
    imports: [
        CommonModule,
        FontAwesomeModule
    ],
    declarations: [
        VisualizationComponent,
        ScreenComponent,
        TooltipComponent,
        ComparisonPanelComponent,
        LegendComponent,
        KeyValuePipe,
        LoadingIndicatorComponent
    ],
    exports: [
        VisualizationComponent
    ],
    providers: [
        MetricService
    ]
})
export class VisualizationModule {
}
