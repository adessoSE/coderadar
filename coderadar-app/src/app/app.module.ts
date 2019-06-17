import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';
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
import { ProjectDashboardComponent } from './view/project-dashboard/project-dashboard.component';
import { ViewCommitComponent } from './view/view-commit/view-commit.component';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatSidenavModule,
  MatToolbarModule,
} from '@angular/material';
import {DependencyRootComponent} from "./levelized-structure-map/dependency-root/dependency-root.component";
import {DependencyTreeProvider} from "./levelized-structure-map/DependencyTreeProvider";

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: MainDashboardComponent },
  { path: 'user-settings', component: UserSettingsComponent },
  { path: 'add-project', component: AddProjectComponent },
  { path: 'project-configure/:id', component: ConfigureProjectComponent },
  { path: 'project-edit/:id', component: EditProjectComponent },
  { path: 'project/:id', component: ProjectDashboardComponent },
  { path: 'project/:id/:name', component: ViewCommitComponent },
  { path: 'structure-map/:projectId/:commitName', component: DependencyRootComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full'}
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
    DependencyRootComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    BrowserModule,
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
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatCheckboxModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
