import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewCommitComponent} from './view-commit.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {Title} from '@angular/platform-browser';
import {of} from 'rxjs';
import {HttpClient, HttpHandler} from '@angular/common/http';

describe('ViewCommitComponent', () => {
  let component: ViewCommitComponent;
  let fixture: ComponentFixture<ViewCommitComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewCommitComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      providers: [
        {provide: Router},
        {provide: UserService},
        Title,
        HttpClient,
        HttpHandler,
        {provide: ActivatedRoute, useValue: {
            params: of({id: 1, name: 'test'})
          }
        }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ViewCommitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
