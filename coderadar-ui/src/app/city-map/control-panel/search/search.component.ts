import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Commit} from "../../../model/commit";
import {filter} from "rxjs/operators";
import {Observable} from "rxjs";
import {text} from "@fortawesome/fontawesome-svg-core";

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

  filterFileOptions(value: string,source:{value:string,displayValue:string}[]): {value:string,displayValue:string}[] {
    if (source === undefined) {
      return [];
    } else if (typeof value !== 'string') {
      return [];
    } else if (value === undefined) {
      return [];
    }
    const lowercaseValue = value.toLowerCase();

    source.forEach(option => {
        var score = 0;
        if(option.displayValue.startsWith(value))score += 10
        if(option.displayValue.includes(value))score +=1;
        option["score"] = score;
    });
    const filteredFiles: {value:string,displayValue:string}[] = source.filter(option => {
      if(option.value.includes(value))return option;
    });

    return filteredFiles.sort(
      (a, b) =>{
        return Math.sign(b["score"] - a["score"]);
      });
  }

  formatFileOptions(option: string): string{
    if(option){
      return option;
    }else{
      return "";
    }
  }

  ngOnInit() {

  }

  handleSearchChanged(chosenItem: string) {
    this.startSearch.emit(chosenItem);
  }
}
