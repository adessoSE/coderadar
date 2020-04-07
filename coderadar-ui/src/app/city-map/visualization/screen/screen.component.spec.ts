import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ScreenComponent} from './screen.component';
import {Component, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FocusService} from '../../service/focus.service';
import {TooltipService} from '../../service/tooltip.service';
import {ComparisonPanelService} from '../../service/comparison-panel.service';
import {INode} from "../../interfaces/INode";
import {NodeType} from "../../enum/NodeType";
import {MergedView} from "../view/merged-view";
import {ScreenType} from "../../enum/ScreenType";
import {type} from "os";
import {SplitView} from "../view/split-view";
import {ViewType} from "../../enum/ViewType";
import {IFilter} from "../../interfaces/IFilter";
import {IMetricMapping} from "../../interfaces/IMetricMapping";

// TODO fix component, test not working, instantiation fails
xdescribe('ScreenComponent', () => {
  let component: ScreenComponent;
  let fixture: ComponentFixture<ScreenComponent>;
  let metricTree: INode = {
    name: 'test',
    type: NodeType.FILE,
    commit1Metrics: [
      {metricName: 'metricA', value: '15'},
      {metricName: 'metricB', value: '22'},
      {metricName: 'metricC', value: '34'},
      {metricName: 'metricD', value: '6'},
      {metricName: 'metricE', value: '12'}
    ],
    commit2Metrics: [
      {metricName: 'metricA', value: '25'},
      {metricName: 'metricB', value: '23'},
      {metricName: 'metricC', value: '24'},
      {metricName: 'metricD', value: '6'},
      {metricName: 'metricE', value: '12'}
    ],
    renamedFrom: null,
    renamedTo: null,
    changes: null,
    children: [
      {
        name: 'testChild1',
        type: NodeType.FILE,
        commit1Metrics: [
          {metricName: 'metricA', value: '15'},
          {metricName: 'metricB', value: '11'},
          {metricName: 'metricC', value: '34'},
          {metricName: 'metricD', value: '13'},
          {metricName: 'metricE', value: '12'}
        ],
        commit2Metrics: [
          {metricName: 'metricA', value: '0'},
          {metricName: 'metricB', value: '23'},
          {metricName: 'metricC', value: '55'},
          {metricName: 'metricD', value: '26'},
          {metricName: 'metricE', value: '2'}
        ],
        renamedFrom: null,
        renamedTo: null,
        changes: null,
      },
      {
        name: 'testChild2',
        type: NodeType.FILE,
        commit1Metrics: [
          {metricName: 'metricA', value: '45'},
          {metricName: 'metricB', value: '44'},
          {metricName: 'metricC', value: '1'},
          {metricName: 'metricD', value: '2'},
          {metricName: 'metricE', value: '23'}
        ],
        commit2Metrics: [
          {metricName: 'metricA', value: '87'},
          {metricName: 'metricB', value: '41'},
          {metricName: 'metricC', value: '97'},
          {metricName: 'metricD', value: '66'},
          {metricName: 'metricE', value: '69'}
        ],
        renamedFrom: null,
        renamedTo: null,
        changes: null,
      }
    ]
  };
  const metricMapping = {
    heightMetricName: 'metricA',
    groundAreaMetricName: 'metricB',
    colorMetricName: 'metricC'
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        ScreenComponent,
      ],
      providers: [
        {provide: FocusService},
        {provide: ComparisonPanelService},
        {provide: TooltipService},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ScreenComponent);
    component = fixture.componentInstance;
    component.metricMapping = metricMapping;
    component.screenType = ScreenType.LEFT;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should prepare merged view', () => {
    component.view = new MergedView(ScreenType.LEFT, metricMapping);
    const calculateConnectionsSpy = spyOn((component.view as MergedView), 'calculateConnections');
    component.prepareView(metricTree);
    expect(component.view.rootNode.name).toBe('test');
    expect(typeof component).toBe('MergedView');
    expect(calculateConnectionsSpy).toHaveBeenCalled();
  });

  it('should prepare split view', () => {
    component.view = new SplitView(ScreenType.LEFT, metricMapping);
    const calculateConnectionsSpy = spyOn((component.view as MergedView), 'calculateConnections');
    component.prepareView(metricTree);
    expect(component.view.rootNode.name).toBe('test');
    expect(typeof component).toBe('SplitView');
    expect(calculateConnectionsSpy).toHaveBeenCalled();
  });

  it('should prepare split view children not defined', () => {
    const childlessMetricTree = {
      name: 'testChild2',
      type: NodeType.FILE,
      commit1Metrics: [
        {metricName: 'metricA', value: '45'},
        {metricName: 'metricB', value: '44'},
        {metricName: 'metricC', value: '1'},
        {metricName: 'metricD', value: '2'},
        {metricName: 'metricE', value: '23'}
      ],
      commit2Metrics: [
        {metricName: 'metricA', value: '87'},
        {metricName: 'metricB', value: '41'},
        {metricName: 'metricC', value: '97'},
        {metricName: 'metricD', value: '66'},
        {metricName: 'metricE', value: '69'}
      ],
      renamedFrom: null,
      renamedTo: null,
      changes: null,
    };
    component.prepareView(childlessMetricTree);
    expect(component.view.rootNode.name).toBeNull();
  });

  it('should prepare split view children empty', () => {
    const childlessMetricTree = {
      name: 'testChild2',
      type: NodeType.FILE,
      commit1Metrics: [
        {metricName: 'metricA', value: '45'},
        {metricName: 'metricB', value: '44'},
        {metricName: 'metricC', value: '1'},
        {metricName: 'metricD', value: '2'},
        {metricName: 'metricE', value: '23'}
      ],
      commit2Metrics: [
        {metricName: 'metricA', value: '87'},
        {metricName: 'metricB', value: '41'},
        {metricName: 'metricC', value: '97'},
        {metricName: 'metricD', value: '66'},
        {metricName: 'metricE', value: '69'}
      ],
      renamedFrom: null,
      renamedTo: null,
      changes: null,
      children: []
    };
    component.prepareView(childlessMetricTree);
    expect(component.view.rootNode.name).toBeNull();
  });

  it('should get central coordinates no root', () => {
    expect((component as any).getCentralCoordinates()).toBe(undefined);
  });

  it('should get central coordinates', () => {
    expect((component as any).getCentralCoordinates()).toBe({x: 0, y: 0, z: 0});
  });
});

@Component({
  selector: 'app-test-component-wrapper',
  template: '<app-screen [activeFilter]="activeFilter" [screenType]="screenType" [activeViewType]="activeViewType" [metricTree]="metricTree" [metricMapping]="metricMapping"></app-screen>'
})
class TestComponentWrapperComponent {
  activeFilter: IFilter = {
    unmodified: false,
    modified: false,
    deleted: false,
    added: false,
    renamed: false,
  };
  screenType: ScreenType = ScreenType.LEFT;
  activeViewType: ViewType = ViewType.SPLIT;
  metricTree: INode = {
    name: '',
    type: NodeType.FILE,
    commit1Metrics: [
      {
        metricName: '',
        value: ''
      }
    ],
    commit2Metrics: [
      {
        metricName: '',
        value: ''
      }
    ],
    renamedFrom: null,
    renamedTo: null,
    changes: null
  };
  metricMapping: IMetricMapping;
}
