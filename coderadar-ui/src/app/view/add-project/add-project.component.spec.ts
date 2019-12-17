import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddProjectComponent} from './add-project.component';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FlexLayoutModule} from '@angular/flex-layout';
import {LayoutModule} from '@angular/cdk/layout';
import {ControlPanelModule} from '../../city-map/control-panel/control-panel.module';
import {VisualizationModule} from '../../city-map/visualization/visualization.module';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatExpansionModule} from '@angular/material/expansion';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

describe('AddProjectComponent', () => {
  let component: AddProjectComponent;
  let fixture: ComponentFixture<AddProjectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddProjectComponent],
      imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        BrowserAnimationsModule,
        BrowserModule,
        FontAwesomeModule,
        BrowserAnimationsModule,
        FlexLayoutModule,
        MatFormFieldModule,
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
        MatSidenavModule,
        MatCheckboxModule,
        BrowserModule,
        FormsModule,
        HttpClientModule,
        ControlPanelModule,
        VisualizationModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatExpansionModule
      ],
      providers: [
        {provide: Router},
        {provide: ActivatedRoute}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AddProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
