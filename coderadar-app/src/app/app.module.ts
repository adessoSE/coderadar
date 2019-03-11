import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AddProjectComponent} from './add-project/add-project.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
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
import {MainDashboardComponent} from './main-dashboard/main-dashboard.component';
import {LayoutModule} from '@angular/cdk/layout';
import {AuthInterceptor} from './auth.interceptor';
import {ConfigureProjectComponent} from './configure-project/configure-project.component';
import {EditProjectComponent} from './edit-project/edit-project.component';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {UserSettingsComponent} from './user-settings/user-settings.component';
import { ProjectDashboardComponent } from './project-dashboard/project-dashboard.component';
import { ViewCommitComponent } from './view-commit/view-commit.component';

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
    ViewCommitComponent
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
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
