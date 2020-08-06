import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Observable} from 'rxjs';
import {VisualizationConfig} from '../../VisualizationConfig';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  @Input() uniqueFileList$: Observable<string[]>;

  @Output() startSearch = new EventEmitter();

  constructor() {
  }

  filterFileOptions(value: string, source: {value: string, displayValue: string, score: number}[]):
    {value: string, displayValue: string}[] {

    if (source === undefined) {
      return [];
    } else if (typeof value !== 'string') {
      return [];
    } else if (value === undefined) {
      return [];
    }
    const lowercaseValue = value.toLowerCase();

    source.forEach(option => {
        let score = 0;
        // Positive conditions
        if (option.displayValue.startsWith(value)) {score += 10; }
        if (option.displayValue.toLowerCase().includes(lowercaseValue)) {score += 1; }
        // Final conditions
        if (option.displayValue === VisualizationConfig.ROOT_NAME) {score = 0; }
        option.score = score;
    });
    const filteredFiles: {value: string, displayValue: string, score: number}[] = source.filter(option => {
      if (option.score <= 0) {return; }
      if (option.value.toLowerCase().includes(lowercaseValue)) {return option; }
    });

    return filteredFiles.sort(
      (a, b) => {
        return Math.sign(b.score - a.score);
      });
  }

  formatFileOptions(option: string): string {
    if (option) {
      return option;
    } else {
      return '';
    }
  }

  ngOnInit() {

  }

  handleSearchChanged(chosenItem: string) {
    this.startSearch.emit(chosenItem);
  }
}
