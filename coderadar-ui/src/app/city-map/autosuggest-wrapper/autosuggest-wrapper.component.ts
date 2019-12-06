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
import {Commit} from '../../model/commit';
import {map} from 'rxjs/operators';

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

  formatCommit(commit: Commit): string {
    if (commit === null || commit === undefined) {
      return '';
    }
    return new Date(commit.timestamp).toUTCString() + ',  ' + commit.name.substring(0, 7) + ', ' + commit.author;
  }

  private _filter(value: string): Commit[] {
    if (this.source === undefined) {
      return [];
    } else if (typeof value !== 'string') {
      return [];
    } else if (value === undefined) {
      return [];
    }

    const lowercaseValue = value.toLowerCase();

    const filteredCommits: any[] = this.source.filter(option => {
      let score = 0;
      const optionAny = option as any;
      if (typeof optionAny !== 'undefined') {
        if (optionAny.author.startsWith(value)) {score += 200; }
        if (optionAny.author.toLowerCase().startsWith(lowercaseValue)) {score += 100; }
        if (optionAny.author.includes(value)) {score += 50; }
        if (optionAny.author.toLowerCase().includes(lowercaseValue)) {score += 25; }
        if (optionAny.name.startsWith(value)) {score += 1000; }
        if (optionAny.name.includes(lowercaseValue)) {score += 500; }
        if (score > 0) {
          optionAny.score = score;
          return option;
        }
      }
    });
    return filteredCommits.sort((a, b) => {
        return Math.sign(b.score - a.score);
    });
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
