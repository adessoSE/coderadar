import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../shared/reducers';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {INode} from '../../interfaces/INode';
import {faCaretDown, faCaretRight, faCaretUp} from '@fortawesome/free-solid-svg-icons';
import {ComparisonPanelService} from '../../service/comparison-panel.service';
import {VisualizationConfig} from '../../VisualizationConfig';
import {Subscription} from 'rxjs';
import {Commit} from '../../../model/commit';

@Component({
  selector: 'app-comparison-panel',
  templateUrl: './comparison-panel.component.html',
  styleUrls: ['./comparison-panel.component.scss']
})
export class ComparisonPanelComponent implements OnInit, OnDestroy {

  faCaretDown = faCaretDown;
  faCaretUp = faCaretUp;
  faCaretRight = faCaretRight;

  @Input() metricMapping: IMetricMapping;
  @Input() leftCommit: Commit;
  @Input() rightCommit: Commit;

  comparisonPanel: HTMLElement;

  subscriptions: Subscription[] = [];

  tableRows: any[] = [];

  elementName: string;

  constructor(
    private store: Store<fromRoot.AppState>,
    private comparisonPanelService: ComparisonPanelService) {
  }

  ngOnInit() {
    this.comparisonPanel = document.querySelector('#comparison-panel') as HTMLElement;

    this.subscriptions.push(
      this.comparisonPanelService.showComparisonPanel$.subscribe((params) => {
        this.elementName = params.elementName;
        this.prepareTableData(params.foundElement);
        this.show();
      })
    );

    this.subscriptions.push(
      this.comparisonPanelService.hideComparisonPanel$.subscribe(() => {
        this.hide();
      })
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription: Subscription) => {
      subscription.unsubscribe();
    });
  }

  handleClose() {
    this.hide();
  }

  prepareTableData(foundElement: INode) {
    const rows = [];
    for (const key of Object.keys(this.metricMapping)) {
      const metricName = this.metricMapping[key];
      let leftCommitValue;
      let rightCommitValue;

      const commit1Element = foundElement.commit1Metrics.find(value => value.metricName === metricName);
      if (foundElement.commit1Metrics && commit1Element) {
        leftCommitValue = commit1Element.value;
      }

      const commit2Element = foundElement.commit2Metrics.find(value => value.metricName === metricName);
      if (foundElement.commit2Metrics && commit2Element) {
        rightCommitValue = commit2Element.value;
      }

      let difference = 0;
      if (leftCommitValue && rightCommitValue) {
        difference = rightCommitValue - leftCommitValue;
      }

      rows.push({
        metricName: VisualizationConfig.getShortNameByMetricName(metricName).shortName,
        leftCommitValue: leftCommitValue || 'N/A',
        rightCommitValue: rightCommitValue || 'N/A',
        difference
      });
    }
    this.tableRows = rows;
  }

  show() {
    this.comparisonPanel.classList.add('open');
  }

  hide() {
    this.comparisonPanel.classList.remove('open');
  }

}
