import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {Observable} from 'rxjs';
import {startWith} from 'rxjs/internal/operators/startWith';
import {FormControl} from '@angular/forms';
import {Commit} from "../../model/commit";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-autosuggest-wrapper',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './autosuggest-wrapper.component.html',
  styleUrls: ['./autosuggest-wrapper.component.scss']
})
export class AutosuggestWrapperComponent implements  OnInit {

  @ViewChild('inputElement') inputElement;

  @Input() model: Commit;
  @Input() source: Commit[];
  @Input() isDisabled: boolean;
  @Input() alignRight = false;
  @Input() label: string;
  @Output() valueChanged = new EventEmitter();
  filteredOptions: Observable<Commit[]>;
  formControl = new FormControl();

  handleValueChanged(chosenModel: any) {
    this.valueChanged.emit(chosenModel);
  }

  formatCommit(commit: Commit) : string {
    if (commit === null || commit === undefined) {
      return '';
    }
    if (commit.hasOwnProperty("analyzed")) {
      return new Date(commit.timestamp).toUTCString() + ',  ' + commit.name.substring(0, 7) + ', ' + commit.author;
    } else {
      return commit.toString();
    }
  }

  private _filter(value: string): Commit[] {
    if (this.source === undefined) {
      return [];
    }else if(typeof value !== "string"){
      return [];
    }else if(value === undefined){
      return [];
    }

    var lowercaseValue = value.toLowerCase();

    var filteredCommits : Commit[] = this.source.filter( option => {
      var score = 0;
      if(typeof option !== "undefined"){
        if(option.author.startsWith(value))score+=200;
        if(option.author.toLowerCase().startsWith(lowercaseValue))score+=100;
        if(option.author.includes(value))score+=50;
        if(option.author.toLowerCase().includes(lowercaseValue))score+=25;
        if(option.name.startsWith(value))score+=1000;
        if(option.name.includes(lowercaseValue))score+=500;
        if(score>0){
          option["score"]=score;
          return option;
        }
      }
    });
    return filteredCommits.sort((a, b) => {
        return Math.sign(b["score"]-a["score"]);
    })
  }

  ngOnInit(): void {
    if (this.model !== null && this.model !== undefined) {
      this.formControl.setValue(this.model);
    }

    this.filteredOptions = this.formControl.valueChanges
      .pipe(
        map(value => this._filter((value)))
      );
  }
}
