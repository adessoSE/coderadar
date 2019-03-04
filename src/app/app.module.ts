import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AddProjectComponent} from './add-project/add-project.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatSidenavModule,
  MatToolbarModule
} from '@angular/material';
import {MainDashboardComponent} from './main-dashboard/main-dashboard.component';
import {LayoutModule} from '@angular/cdk/layout';

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: MainDashboardComponent },
  { path: 'add-project', component: AddProjectComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    AddProjectComponent,
    MainDashboardComponent
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
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
