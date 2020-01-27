import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {LoginComponent} from './view/login/login.component';
import {RegisterComponent} from './view/register/register.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AddProjectComponent} from './view/add-project/add-project.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MainDashboardComponent} from './view/main-dashboard/main-dashboard.component';
import {LayoutModule} from '@angular/cdk/layout';
import {AuthInterceptor} from './auth.interceptor';
import {ConfigureProjectComponent} from './view/configure-project/configure-project.component';
import {EditProjectComponent} from './view/edit-project/edit-project.component';
import {HeaderComponent} from './view/header/header.component';
import {FooterComponent} from './view/footer/footer.component';
import {UserSettingsComponent} from './view/user-settings/user-settings.component';
import {ProjectDashboardComponent} from './view/project-dashboard/project-dashboard.component';
import {ViewCommitComponent} from './view/view-commit/view-commit.component';
import { ChartsModule } from 'ng2-charts';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule, MatPaginatorModule,
  MatSidenavModule,
  MatToolbarModule,
  MatTableModule,
  MatSortModule,
  MatSelectModule,
  MatDialogModule,
  MatTabsModule,
  MatRadioModule,
  MatProgressBarModule,
  MatDatepickerModule,
  MatChipsModule,
  MatBadgeModule,
  MatTooltipModule,
} from '@angular/material';
import {ControlPanelModule} from './city-map/control-panel/control-panel.module';
import {VisualizationModule} from './city-map/visualization/visualization.module';
import {getReducers, REDUCER_TOKEN} from './city-map/shared/reducers';
import {StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {AppEffects} from './city-map/shared/effects';
import {FocusService} from './city-map/service/focus.service';
import {TooltipService} from './city-map/service/tooltip.service';
import {ComparisonPanelService} from './city-map/service/comparison-panel.service';
import {environment} from '../environments/environment';
import {CityViewComponent} from './view/city-view/city-view.component';
import {CityViewHeaderComponent} from './view/city-view/city-view-header/city-view-header.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
/**
 * Dashboard imports
 */
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';

import { HotspotWidgetComponent } from './dashboard/hotspot-widget/hotspot-widget.component';
import { HotspotConfigurationComponent } from './dashboard/dialogs/hotspot-configuration/hotspot-configuration.component';
import { MetricPipe } from './dashboard/pipes/metric.pipe';
import { IssueWidgetComponent } from './dashboard/issue-widget/issue-widget.component';
import { FilePipe } from './dashboard/pipes/file.pipe';
import { CoverageWidgetComponent } from './dashboard/coverage-widget/coverage-widget.component';
import { PeriodWidgetComponent } from './dashboard/period-widget/period-widget.component';
import { GoalWidgetComponent } from './dashboard/goal-widget/goal-widget.component';
import { GoalConfigurationComponent } from './dashboard/dialogs/goal-configuration/goal-configuration.component';
import { PeriodConfigurationComponent } from './dashboard/dialogs/period-configuration/period-configuration.component';
import { MatNativeDateModule} from '@angular/material/core';
import { CommitWidgetComponent } from './dashboard/commit-widget/commit-widget.component';
import { ArchitectureWidgetComponent } from './dashboard/architecture-widget/architecture-widget.component';
import { TodoWidgetComponent } from './dashboard/todo-widget/todo-widget.component';
import { HeadMonopolyWidgetComponent } from './dashboard/head-monopoly-widget/head-monopoly-widget.component';
import { MaterialDashboardComponent } from './dashboard/material-dashboard/material-dashboard.component';


const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'dashboard', component: MainDashboardComponent},
  {path: 'user-settings', component: UserSettingsComponent},
  {path: 'add-project', component: AddProjectComponent},
  {path: 'project-configure/:id', component: ConfigureProjectComponent},
  {path: 'city/:id', component: CityViewComponent},
  {path: 'project-edit/:id', component: EditProjectComponent},
  {path: 'project/:id', component: ProjectDashboardComponent},
  {path: 'project/:id/:name', component: DashboardComponent},
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    AddProjectComponent,
    MainDashboardComponent,
    ConfigureProjectComponent,
    EditProjectComponent,
    HeaderComponent,
    FooterComponent,
    UserSettingsComponent,
    ProjectDashboardComponent,
    ViewCommitComponent,
    CityViewComponent,
    CityViewHeaderComponent,
    DashboardComponent,
    MaterialDashboardComponent,
    HotspotWidgetComponent,
    HotspotConfigurationComponent,
    MetricPipe,
    IssueWidgetComponent,
    FilePipe,
    CoverageWidgetComponent,
    PeriodWidgetComponent,
    GoalWidgetComponent,
    GoalConfigurationComponent,
    PeriodConfigurationComponent,
    CommitWidgetComponent,
    ArchitectureWidgetComponent,
    TodoWidgetComponent,
    HeadMonopolyWidgetComponent,
  ],
  imports: [
    MatBadgeModule,
    MatChipsModule,
    MatDatepickerModule,
    MatProgressBarModule,
    MatSelectModule,
    MatDialogModule,
    MatTabsModule,
    MatTableModule,
    MatSortModule,
    MatRadioModule,
    ChartsModule,
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatGridListModule,
    MatMenuModule,
    MatListModule,
    MatIconModule,
    RouterModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatCheckboxModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ControlPanelModule,
    VisualizationModule,
    StoreModule.forRoot(REDUCER_TOKEN),
    EffectsModule.forRoot([AppEffects]),
    environment.production ? [] : StoreDevtoolsModule.instrument({maxAge: 50}),
    MatPaginatorModule,
    MatNativeDateModule,
    MatTooltipModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    FocusService,
    TooltipService,
    ComparisonPanelService,
    {
      provide: REDUCER_TOKEN,
      useFactory: getReducers,
    }],
  bootstrap: [AppComponent],
  entryComponents: [HotspotConfigurationComponent, GoalConfigurationComponent, PeriodConfigurationComponent]
})
export class AppModule {
}
