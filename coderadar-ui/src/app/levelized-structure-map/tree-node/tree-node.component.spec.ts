import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TreeNodeComponent } from './tree-node.component';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

describe('TreeNodeComponent', () => {
  let component: TreeNodeComponent;
  let fixture: ComponentFixture<TreeNodeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        TreeNodeComponent
      ],
      imports: [
        HttpClientTestingModule,
      ],
      providers: [],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(TreeNodeComponent);
    component = fixture.componentInstance;
    component.node = {
      filename: 'org',
      path: 'org',
      packageName: 'org',
      level: 0,
      changed: null,
      children: [],
      dependencies: []
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should generate className class', () => {
    expect(component.getClassString({
      filename: 'org',
      path: 'org',
      packageName: 'org',
      level: 0,
      changed: null,
      children: [],
      dependencies: []
    })).toEqual('class');
  });

  it('should generate className package', () => {
    expect(component.getClassString({
      filename: 'org',
      path: 'org',
      packageName: 'org',
      level: 0,
      changed: null,
      children: [{}, {}],
      dependencies: []
    })).toEqual('package');
  });

  it('should generate className dependency', () => {
    expect(component.getClassString({
      filename: 'org',
      path: 'org',
      packageName: 'org',
      level: 0,
      changed: null,
      children: [],
      dependencies: [{}, {}]
    })).toEqual('class--dependency');
  });

  it('should generate className class added', () => {
    expect(component.getClassString({
      filename: 'org',
      path: 'org',
      packageName: 'org',
      level: 0,
      changed: 'ADD',
      children: [],
      dependencies: []
    })).toEqual('class added');
  });

  it('should generate className class deleted', () => {
    expect(component.getClassString({
      filename: 'org',
      path: 'org',
      packageName: 'org',
      level: 0,
      changed: 'DELETE',
      children: [],
      dependencies: []
    })).toEqual('class deleted');
  });
});
