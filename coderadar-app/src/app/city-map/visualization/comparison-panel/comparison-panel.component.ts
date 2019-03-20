import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromRoot from '../../shared/reducers';
import {ICommit} from '../../interfaces/ICommit';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {INode} from '../../interfaces/INode';
import {faCaretDown, faCaretRight, faCaretUp} from '@fortawesome/free-solid-svg-icons';
import {ComparisonPanelService} from '../../../service/comparison-panel.service';
import {AppConfig} from '../../../AppConfig';
import {Subscription} from 'rxjs';

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
    @Input() leftCommit: ICommit;
    @Input() rightCommit: ICommit;

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
            if (foundElement.commit1Metrics && foundElement.commit1Metrics[metricName]) {
                leftCommitValue = foundElement.commit1Metrics[metricName];
            }

            let rightCommitValue;
            if (foundElement.commit2Metrics && foundElement.commit2Metrics[metricName]) {
                rightCommitValue = foundElement.commit2Metrics[metricName];
            }

            let difference = 0;
            if (leftCommitValue && rightCommitValue) {
                difference = rightCommitValue - leftCommitValue;
            }

            rows.push({
                metricName: AppConfig.getShortNameByMetricName(metricName).shortName,
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
