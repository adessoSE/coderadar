import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditProjectComponent} from './edit-project.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {of} from 'rxjs';
import {HttpClient, HttpHandler} from '@angular/common/http';

describe('EditProjectComponent', () => {
  let component: EditProjectComponent;
  let fixture: ComponentFixture<EditProjectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditProjectComponent],
      imports: [
        FormsModule // ngModel
      ],
      providers: [
        {provide: MatSnackBar},
        {provide: UserService},
        HttpClient,
        HttpHandler,
        {provide: ActivatedRoute, useValue: {
            params: of({ id: 1 })
          }
        },
        {provide: Router}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EditProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
