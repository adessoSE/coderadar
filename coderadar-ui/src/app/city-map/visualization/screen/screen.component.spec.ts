import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ScreenComponent} from './screen.component';
import {Component, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {FocusService} from '../../service/focus.service';
import {TooltipService} from '../../service/tooltip.service';
import {ComparisonPanelService} from '../../service/comparison-panel.service';
import {IFilter} from '../../interfaces/IFilter';
import {ScreenType} from '../../enum/ScreenType';
import {ViewType} from '../../enum/ViewType';
import {INode} from '../../interfaces/INode';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {NodeType} from '../../enum/NodeType';

describe('ScreenComponent', () => {
  let component: ScreenComponent;
  let fixture: ComponentFixture<TestComponentWrapperComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        TestComponentWrapperComponent
      ],
      providers: [
        {provide: FocusService},
        {provide: ComparisonPanelService},
        {provide: TooltipService}
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
