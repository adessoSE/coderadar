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
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatSnackBarModule,
  MatListModule,
  MatMenuModule,
  MatPaginatorModule,
  MatSidenavModule,
  MatToolbarModule,
  MatProgressSpinnerModule,
  MatExpansionModule, MatTabsModule,
  MatDialogModule, MatTreeModule,
  MatTooltipModule,
  MAT_DIALOG_DEFAULT_OPTIONS, MatDialogConfig,
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
import {DependencyRootComponent} from './levelized-structure-map/dependency-root/dependency-root.component';
import {DependencyCompareComponent} from './levelized-structure-map/dependency-compare/dependency-compare.component';
import {TreeNodeComponent} from './levelized-structure-map/tree-node/tree-node.component';
import {MatSelectModule} from '@angular/material/select';
import {DragScrollModule} from 'ngx-drag-scroll';
import {PinchZoomModule} from 'ngx-pinch-zoom';
import {ScrollingModule} from '@angular/cdk/scrolling';
import { ListViewComponent } from './view/project-dashboard/list-view/list-view.component';
import { BranchViewComponent } from './view/project-dashboard/branch-view/branch-view.component';
import {ContributorCardComponent, ContributorDialogComponent} from './components/contributor-card/contributor-card.component';
import { FileViewComponent } from './view/file-view/file-view.component';
import { TeamsDashboardComponent } from './view/teams-dashboard/teams-dashboard.component';
import { AddTeamComponent } from './view/add-team/add-team.component';
import { SidenavContentComponent } from './view/sidenav-content/sidenav-content.component';
import {ContributorMergeDialogComponent} from './components/contributor-merge-dialog/contributor-merge-dialog.component';
import {DeleteProjectDialogComponent} from "./components/delete-project-dialog/delete-project-dialog.component";
import {AddProjectToTeamDialogComponent} from "./components/add-project-to-team-dialog/add-project-to-team-dialog.component";
import {DeleteTeamDialogComponent} from "./components/delete-team-dialog/delete-team-dialog.component";

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'dashboard', component: MainDashboardComponent},
  {path: 'teams', component: TeamsDashboardComponent},
  {path: 'add-team', component: AddTeamComponent},
  {path: 'user-settings', component: UserSettingsComponent},
  {path: 'add-project', component: AddProjectComponent},
  {path: 'project-configure/:id', component: ConfigureProjectComponent},
  {path: 'city/:id', component: CityViewComponent},
  {path: 'project-edit/:id', component: EditProjectComponent},
  {path: 'project/:id', component: ProjectDashboardComponent},
  {path: 'project/:id/:name', component: ViewCommitComponent},
  {path: 'project/:projectId/:commitName/dependency-map', component: DependencyRootComponent},
  {path: 'project/:projectId/:commitName1/:commitName2/dependency-map', component: DependencyCompareComponent},
  {path: 'project/:projectId/:commitHash/files', component: FileViewComponent},
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
    TeamsDashboardComponent,
    ViewCommitComponent,
    CityViewComponent,
    DependencyRootComponent,
    ContributorMergeDialogComponent,
    DependencyCompareComponent,
    TreeNodeComponent,
    CityViewHeaderComponent,
    ListViewComponent,
    BranchViewComponent,
    FileViewComponent,
    TeamsDashboardComponent,
    AddProjectToTeamDialogComponent,
    AddTeamComponent,
    SidenavContentComponent,
    ContributorDialogComponent,
    ContributorCardComponent,
    FileViewComponent,
    DeleteProjectDialogComponent,
    DeleteTeamDialogComponent
  ],
  imports: [
    BrowserModule,
    MatDialogModule,
    MatTreeModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes, {scrollPositionRestoration: 'enabled'}),
    BrowserAnimationsModule,
    BrowserModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    DragScrollModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    MatSnackBarModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatGridListModule,
    MatMenuModule,
    MatListModule,
    MatIconModule,
    RouterModule,
    LayoutModule,
    MatToolbarModule,
    MatTabsModule,
    ScrollingModule,
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
    MatProgressSpinnerModule,
    MatSelectModule,
    PinchZoomModule,
    MatExpansionModule,
    MatTooltipModule
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
    },
    {
      provide: MatDialogConfig,
      useFactory: MAT_DIALOG_DEFAULT_OPTIONS
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    AddProjectToTeamDialogComponent,
    ContributorMergeDialogComponent,
    ContributorDialogComponent,
    DeleteProjectDialogComponent,
    DeleteTeamDialogComponent
  ]
})
export class AppModule {
}
