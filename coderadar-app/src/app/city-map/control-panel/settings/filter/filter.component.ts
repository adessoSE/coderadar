import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {IFilter} from '../../../interfaces/IFilter';

declare var $: any;

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

  filterList: string[] = [];
  selectedList: string[] = [];

  @Input() activeFilter: IFilter;

  @Output() filterChanged = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
    this.filterList = ['unmodified', 'added', 'deleted', 'modified', 'renamed'];
    this.selectedList = this.filterList.filter(e => {
      if (e === 'unmodified') {
        return this.activeFilter.unmodified;
      } else if (e === 'added') {
        return this.activeFilter.added;
      } else if (e === 'deleted') {
        return this.activeFilter.deleted;
      } else if (e === 'modified') {
        return this.activeFilter.modified;
      } else if (e === 'renamed') {
        return this.activeFilter.renamed;
      }
    });
  }

  handleFilterChanged() {
    this.activeFilter.unmodified = this.selectedList.indexOf('unmodified') !== -1;
    this.activeFilter.added = this.selectedList.indexOf('added') !== -1;
    this.activeFilter.deleted = this.selectedList.indexOf('deleted') !== -1;
    this.activeFilter.modified = this.selectedList.indexOf('modified') !== -1;
    this.activeFilter.renamed = this.selectedList.indexOf('renamed') !== -1;
    this.filterChanged.emit(this.activeFilter);
  }
}
