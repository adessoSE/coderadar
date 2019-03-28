import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild, ViewEncapsulation} from '@angular/core';
import {Commit} from '../../model/commit';
import {from, Observable} from 'rxjs';
import {startWith} from 'rxjs/internal/operators/startWith';
import {catchError, map} from 'rxjs/operators';
import {FormControl} from '@angular/forms';
import {isNull} from 'util';

@Component({
    selector: 'app-autosuggest-wrapper',
    encapsulation: ViewEncapsulation.None,
    templateUrl: './autosuggest-wrapper.component.html',
    styleUrls: ['./autosuggest-wrapper.component.scss']
})
export class AutosuggestWrapperComponent implements OnInit, OnChanges {

  @ViewChild('inputElement') inputElement;

  @Input() model: any;
  @Input() source: any;
  @Input() isDisabled: boolean;
  @Input() alignRight = false;
  @Input() label: string;
  @Output() valueChanged = new EventEmitter();

  filteredOptions: Observable<any[]>;
  formControl = new FormControl();

  ngOnInit() {}

  private _filter(value: any): string[] {
    let filterValue = '';
    if (value.hasOwnProperty('name')) {
      filterValue = value.name.toLowerCase();
    } else {
      filterValue = value.toLowerCase();
    }

    return this.source.filter(option => {
      if (option.hasOwnProperty('name')) {
        return option.name.toLowerCase().includes(filterValue)
          || option.author.toLowerCase().includes(filterValue)
          || new Date(option.timestamp).toDateString().toLowerCase().includes(filterValue);
      } else {
        return option.toLowerCase().includes(filterValue);
      }
    });
  }

  handleValueChanged(chosenModel: any) {
      this.valueChanged.emit(chosenModel);
  }

  formatValue(value: any) {
    if (value === null) {
      return '';
    }
    if (value.hasOwnProperty('name')) {
      return value.name + ', ' + value.author + ', ' + new Date(value.timestamp).toDateString();
    } else {
      return value;
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    this.filteredOptions = this.formControl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

}
