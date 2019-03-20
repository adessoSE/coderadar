import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {faFilter} from '@fortawesome/free-solid-svg-icons';
import {IFilter} from '../../../interfaces/IFilter';

declare var $: any;

@Component({
    selector: 'app-filter',
    templateUrl: './filter.component.html',
    styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

    faFilter = faFilter;

    @Input() activeFilter: IFilter;

    @Output() filterChanged = new EventEmitter();

    constructor() {
    }

    ngOnInit() {
        // prevent bootstrap dropdown from being closed by clicking on its content
        /*$(document).on('click', '#filter-dropdown', (e) => {
            e.stopPropagation();
        });*/
    }

    handleFilterChanged() {
        this.filterChanged.emit(this.activeFilter);
    }

}
