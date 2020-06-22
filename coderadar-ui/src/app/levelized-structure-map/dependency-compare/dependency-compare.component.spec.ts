import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {By} from '@angular/platform-browser';
import {DependencyCompareComponent} from './dependency-compare.component';
import testdata from './testdata.json';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterTestingModule} from '@angular/router/testing';
import {of} from 'rxjs';
import {TreeNodeComponent} from '../tree-node/tree-node.component';
import {AppComponent} from '../../app.component';

let routerSpy;
let fixture: ComponentFixture<DependencyCompareComponent>;

describe('DependencyCompareComponent', () => {
  let component: DependencyCompareComponent;
  let http;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        DependencyCompareComponent,
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [
        {provide: Router},
        {provide: ActivatedRoute, useValue: {
            params: of({id: 1})
          }},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DependencyCompareComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake(() => {});
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

describe('DependencyCompareComponent', () => {
  let component: DependencyCompareComponent;
  let http;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        DependencyCompareComponent,
        TreeNodeComponent
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [
        {provide: Router},
        {
          provide: ActivatedRoute, useValue: {
            params: of({projectId: 1, commitName1: '1', commitName2: '2'})
          }
        },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DependencyCompareComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake(() => {});
  });

  it('should return a dependencyTree comparing two commits made from test data', () => {
    expect(testdata).toBeTruthy();
    spyOn(component, 'getData').and.callThrough();

    fixture.detectChanges();

    expect(component.getData).toHaveBeenCalled();
    http.expectOne(`${AppComponent.getApiUrl()}analyzers/1/structureMap/1/2`).flush(testdata, {
      status: 200,
      statusText: 'Ok',
      url: 'api/analyzers/1/structureMap/1/2',
    });

    fixture.whenStable().then(() => {
      expect(component.node).toEqual(testdata);
      expect(fixture.debugElement.query(By.css('[id="3dependencyTree"]'))).toBeTruthy();
      fixture.detectChanges();
      fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('[id="3list__root"]'))).toBeTruthy();
        expect(fixture.debugElement.query(By.css('.filename-span'))).toBeTruthy();
        expect(fixture.debugElement.queryAll(By.css('.filename-span'))[0]).toBeTruthy();
        expect(fixture.debugElement.queryAll(By.css('.filename-span'))[0].nativeElement.innerText).toEqual(' example/');
      });
    });
  });
});

describe('DependencyCompareComponent', () => {
  let component: DependencyCompareComponent;
  let http;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        DependencyCompareComponent,
        TreeNodeComponent
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [
        {provide: Router},
        {
          provide: ActivatedRoute, useValue: {
            params: of({projectId: 1, commitName1: '1', commitName2: 'null'})
          }
        },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DependencyCompareComponent);
    component = fixture.componentInstance;
    http = TestBed.get(HttpTestingController);
    routerSpy = spyOn(Router.prototype, 'navigate').and.callFake(() => {});
  });

  it('should return a dependencyTree comparing two commits made from test data', () => {
    expect(testdata).toBeTruthy();
    spyOn(component, 'getData').and.callThrough();

    fixture.detectChanges();

    expect(component.getData).toHaveBeenCalled();
    http.expectOne(`${AppComponent.getApiUrl()}analyzers/1/structureMap/1`).flush(testdata, {
      status: 200,
      statusText: 'Ok',
      url: 'api/analyzers/1/structureMap/1/2',
    });

    fixture.whenStable().then(() => {
      expect(component.node).toEqual(testdata);
      expect(fixture.debugElement.query(By.css('[id="3dependencyTree"]'))).toBeTruthy();
      fixture.detectChanges();
      fixture.whenStable().then(() => {
        expect(fixture.debugElement.query(By.css('[id="3list__root"]'))).toBeTruthy();
        expect(fixture.debugElement.query(By.css('.filename-span'))).toBeTruthy();
        expect(fixture.debugElement.queryAll(By.css('.filename-span'))[0]).toBeTruthy();
        expect(fixture.debugElement.queryAll(By.css('.filename-span'))[0].nativeElement.innerText).toEqual(' example/');
      });
    });
  });
});
