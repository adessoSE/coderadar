import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchComponent} from './search.component';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FlexLayoutModule} from '@angular/flex-layout';
import {RouterModule} from '@angular/router';
import {LayoutModule} from '@angular/cdk/layout';
import {VisualizationModule} from '../../visualization/visualization.module';
import {MatCardModule} from '@angular/material/card';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatButtonModule} from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatMenuModule} from '@angular/material/menu';
import {MatList, MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

describe('SearchComponent', () => {
  let component: SearchComponent;
  let fixture: ComponentFixture<SearchComponent>;
  const file0 = {value: 'root', displayValue: 'root'};
  const file1 = {value: 'root/src/main/java/FirstTestFile.java', displayValue: 'FirstTestFile.java'};
  const file2 = {value: 'root/src/main/java/SecondTestFile.java', displayValue: 'SecondTestFile.java'};
  const file3 = {value: 'root/src/main/java/ThirdTestFile.java', displayValue: 'ThirdTestFile.java'};
  const file4 = {value: 'root/src/main/java/FourthTestFile.java', displayValue: 'FourthTestFile.java'};
  const file5 = {value: 'root/src/main/java/FifthTestFile.java', displayValue: 'FifthTestFile.java'};
  const file6 = {value: 'root/src/main/java/SixthTestFile.java', displayValue: 'SixthTestFile.java'};
  const file7 = {value: 'root/src/main/java/FileOfFirstTest.java', displayValue: 'FileOfFirstTest.java'};
  const source = [file0, file7, file1, file2, file3, file4, file5, file6];

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        SearchComponent
      ],
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
        VisualizationModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatExpansionModule
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(SearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should format file options', () => {
    expect(component.formatFileOptions('test')).toBe('test');
    expect(component.formatFileOptions(undefined)).toBe('');
    expect(component.formatFileOptions(null)).toBe('');
  });

  it('should filter file options', () => {
    expect(component.filterFileOptions('test', undefined).length).toBe(0);
    expect(component.filterFileOptions(undefined, source).length).toBe(0);
    expect(component.filterFileOptions('test', []).length).toBe(0);
  });

  it('should filter file options value th', () => {
    const filtered = component.filterFileOptions('th', source);
    expect(filtered.length).toBe(4);
    expect(filtered[0].displayValue).toBe('ThirdTestFile.java');
    expect(filtered[1].displayValue).toBe('FourthTestFile.java');
    expect(filtered[2].displayValue).toBe('FifthTestFile.java');
    expect(filtered[3].displayValue).toBe('SixthTestFile.java');
  });

  it('should filter file options value thTest', () => {
    const filtered = component.filterFileOptions('thTest', source);
    expect(filtered.length).toBe(3);
    expect(filtered[0].displayValue).toBe('FourthTestFile.java');
    expect(filtered[1].displayValue).toBe('FifthTestFile.java');
    expect(filtered[2].displayValue).toBe('SixthTestFile.java');
  });

  it('should filter file options empty value', () => {
    const filtered = component.filterFileOptions('', source);
    expect(filtered.length).toBe(7);
    expect(filtered[0].displayValue).toBe('FileOfFirstTest.java');
    expect(filtered[1].displayValue).toBe('FirstTestFile.java');
    expect(filtered[2].displayValue).toBe('SecondTestFile.java');
    expect(filtered[3].displayValue).toBe('ThirdTestFile.java');
    expect(filtered[4].displayValue).toBe('FourthTestFile.java');
    expect(filtered[5].displayValue).toBe('FifthTestFile.java');
    expect(filtered[6].displayValue).toBe('SixthTestFile.java');
  });

  it('should filter file options difference between starts with and includes', () => {
    const filtered = component.filterFileOptions('First', source);
    expect(filtered.length).toBe(2);
    expect(filtered[0].displayValue).toBe('FirstTestFile.java');
    expect(filtered[1].displayValue).toBe('FileOfFirstTest.java');
  });
});
