import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  @Input() uniqueFileList: string[] = [];

  @Output() startSearch = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  handleSearchChanged(chosenItem: string) {
    this.startSearch.emit(chosenItem);
  }

  autocompleteListFormatter(data: string): string {
    return `<span title="${data}">${data}</span>`;
  }

}
