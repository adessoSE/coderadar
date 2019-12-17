import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ControlPanelComponent} from './control-panel.component';
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
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ScannedActionsSubject} from '@ngrx/store';
import {FocusService} from '../service/focus.service';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FlexLayoutModule} from '@angular/flex-layout';
import {RouterModule} from '@angular/router';
import {LayoutModule} from '@angular/cdk/layout';
import {ControlPanelModule} from './control-panel.module';
import {VisualizationModule} from '../visualization/visualization.module';
import {MatCardModule} from '@angular/material/card';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatButtonModule} from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatMenuModule} from '@angular/material/menu';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatExpansionModule} from '@angular/material/expansion';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AppEffects} from '../shared/effects';
import {Actions} from '@ngrx/effects';
import {RouterTestingModule} from '@angular/router/testing';
import {Component, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {IFilter} from '../interfaces/IFilter';

describe('ControlPanelComponent', () => {
  let component: ControlPanelComponent;
  let fixture: ComponentFixture<TestComponentWrapperComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MatInputModule, MatAutocompleteModule, MatFormFieldModule, FormsModule,
        BrowserAnimationsModule, MatSelectModule, MatCheckboxModule, MatRadioModule,
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
        HttpClientTestingModule,
        ControlPanelModule,
        VisualizationModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatExpansionModule,
        RouterTestingModule
      ],
      declarations: [
        TestComponentWrapperComponent
      ],
      providers: [
        {provide: FocusService},
        AppEffects,
        Actions,
        ScannedActionsSubject
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(TestComponentWrapperComponent);
    component = fixture.debugElement.children[0].componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

@Component({
  selector: 'app-test-component-wrapper',
  template: '<app-filter [activeFilter]="activeFilter"></app-filter>'
})
class TestComponentWrapperComponent {
  activeFilter: IFilter = {
    unmodified: false,
    modified: false,
    deleted: false,
    added: false,
    renamed: false,
  };
}
