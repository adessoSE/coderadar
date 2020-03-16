import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ComparisonPanelComponent} from './comparison-panel.component';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {Store} from '@ngrx/store';
import {ComparisonPanelService} from '../../service/comparison-panel.service';
import {VisualizationModule} from '../visualization.module';
import {INode} from "../../interfaces/INode";
import {NodeType} from "../../enum/NodeType";
import {MetricValue} from "../../../model/metric-value";
import {IPackerElement} from "../../interfaces/IPackerElement";
import {VisualizationConfig} from "../../VisualizationConfig";

describe('ComparisonPanelComponent', () => {
  let component: ComparisonPanelComponent;
  let fixture: ComponentFixture<ComparisonPanelComponent>;
  let node: INode = {
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
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        VisualizationModule
      ],
      providers: [
        ComparisonPanelService,
        {provide: Store}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ComparisonPanelComponent);
    component = fixture.componentInstance;
    component.metricMapping = {
      heightMetricName: 'metricA',
      groundAreaMetricName: 'metricB',
      colorMetricName: 'metricC'
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should prepare table data', () => {
    component.prepareTableData(node);
    expect(component.tableRows).toEqual([
      {metricName: 'metricA', leftCommitValue: 15, rightCommitValue: 25, difference: 10},
      {metricName: 'metricB', leftCommitValue: 22, rightCommitValue: 23, difference: 1},
      {metricName: 'metricC', leftCommitValue: 34, rightCommitValue: 24, difference: -10}
    ]);
  });

  it('should prepare table data empty left commits', () => {
    let emptyLeftNode: INode = {
      name: 'test',
      type: NodeType.FILE,
      commit1Metrics: [],
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
    };

    component.prepareTableData(emptyLeftNode);
    expect(component.tableRows).toEqual([
      {metricName: 'metricA', leftCommitValue: 'N/A', rightCommitValue: 25, difference: 25},
      {metricName: 'metricB', leftCommitValue: 'N/A', rightCommitValue: 23, difference: 23},
      {metricName: 'metricC', leftCommitValue: 'N/A', rightCommitValue: 24, difference: 24}
    ]);
  });

  it('should prepare table data empty right commits', () => {
    let emptyLeftNode: INode = {
      name: 'test',
      type: NodeType.FILE,
      commit1Metrics: [
        {metricName: 'metricA', value: '15'},
        {metricName: 'metricB', value: '22'},
        {metricName: 'metricC', value: '34'},
        {metricName: 'metricD', value: '6'},
        {metricName: 'metricE', value: '12'}
      ],
      commit2Metrics: [],
      renamedFrom: null,
      renamedTo: null,
      changes: null,
    };

    component.prepareTableData(emptyLeftNode);
    expect(component.tableRows).toEqual([
      {metricName: 'metricA', leftCommitValue: 15, rightCommitValue: 'N/A', difference: -15},
      {metricName: 'metricB', leftCommitValue: 22, rightCommitValue: 'N/A', difference: -22},
      {metricName: 'metricC', leftCommitValue: 34, rightCommitValue: 'N/A', difference: -34}
    ]);
  });

  it('should prepare table data empty left commits', () => {
    let emptyNode: INode = {
      name: 'test',
      type: NodeType.FILE,
      commit1Metrics: [],
      commit2Metrics: [],
      renamedFrom: null,
      renamedTo: null,
      changes: null,
    };

    component.prepareTableData(emptyNode);
    expect(component.tableRows).toEqual([
      {metricName: 'metricA', leftCommitValue: 'N/A', rightCommitValue: 'N/A', difference: 0},
      {metricName: 'metricB', leftCommitValue: 'N/A', rightCommitValue: 'N/A', difference: 0},
      {metricName: 'metricC', leftCommitValue: 'N/A', rightCommitValue: 'N/A', difference: 0}
    ]);
  });

  it('should prepare table data empty metric mappings', () => {
    component.metricMapping = {
      heightMetricName: '',
      groundAreaMetricName: '',
      colorMetricName: ''
    };
    component.prepareTableData(node);
    expect(component.tableRows).toEqual([
      {metricName: '', leftCommitValue: 'N/A', rightCommitValue: 'N/A', difference: 0},
      {metricName: '', leftCommitValue: 'N/A', rightCommitValue: 'N/A', difference: 0},
      {metricName: '', leftCommitValue: 'N/A', rightCommitValue: 'N/A', difference: 0}
    ]);
  });

  it('should prepare table data metric mappings null', () => {
    component.metricMapping = {
      heightMetricName: null,
      groundAreaMetricName: null,
      colorMetricName: null
    };
    expect(() => component.prepareTableData(node)).toThrow();
    expect(() => component.prepareTableData(node)).toThrowError();
    expect(() => component.prepareTableData(node)).toThrowError('metricName is null');
  });

  it('should prepare table data metric mappings undefined', () => {
    component.metricMapping = {
      heightMetricName: undefined,
      groundAreaMetricName: undefined,
      colorMetricName: undefined
    };
    expect(() => component.prepareTableData(node)).toThrow();
    expect(() => component.prepareTableData(node)).toThrowError();
    expect(() => component.prepareTableData(node)).toThrowError('metricName is undefined');
  });

  it('should prepare table data metric mapping null', () => {
    component.metricMapping = null;
    expect(() => component.prepareTableData(node)).toThrow();
    expect(() => component.prepareTableData(node)).toThrowError();
    expect(() => component.prepareTableData(node)).toThrowError('can\'t convert null to object');
  });

  it('should prepare table data metric mapping undefined', () => {
    component.metricMapping = undefined;
    expect(() => component.prepareTableData(node)).toThrow();
    expect(() => component.prepareTableData(node)).toThrowError();
    expect(() => component.prepareTableData(node)).toThrowError('can\'t convert undefined to object');
  });
});
