import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ConfigureProjectComponent} from './configure-project.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserService} from '../../service/user.service';
import {Title} from '@angular/platform-browser';
import {RouterTestingModule} from '@angular/router/testing';
import {of} from 'rxjs';
import {HttpClient, HttpHandler} from '@angular/common/http';

describe('ConfigureProjectComponent', () => {
  let component: ConfigureProjectComponent;
  let fixture: ComponentFixture<ConfigureProjectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfigureProjectComponent],
      imports: [
        FormsModule, // ngModel
        RouterTestingModule
      ],
      providers: [
        {provide: MatSnackBar},
        {provide: UserService},
        HttpClient,
        HttpHandler,
        {provide: Title},
        {provide: ActivatedRoute, useValue: {
            params: of({ id: 1 })
          }},
        {provide: Router}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ConfigureProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
