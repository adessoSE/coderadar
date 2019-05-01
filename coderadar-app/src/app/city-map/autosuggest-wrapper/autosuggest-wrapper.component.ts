import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild, ViewEncapsulation} from '@angular/core';
import {Observable} from 'rxjs';
import {startWith} from 'rxjs/internal/operators/startWith';
import {map} from 'rxjs/operators';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-autosuggest-wrapper',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './autosuggest-wrapper.component.html',
  styleUrls: ['./autosuggest-wrapper.component.scss']
})
export class AutosuggestWrapperComponent implements OnChanges, OnInit {

  @ViewChild('inputElement') inputElement;

  @Input() model: any;
  @Input() source: any;
  @Input() isDisabled: boolean;
  @Input() alignRight = false;
  @Input() label: string;
  @Output() valueChanged = new EventEmitter();

  filteredOptions: Observable<any[]>;
  formControl = new FormControl();

  handleValueChanged(chosenModel: any) {
    this.valueChanged.emit(chosenModel);
  }

  formatValue(value: any) {
    if (value === null || value === undefined) {
      return '';
    }
    if (value.hasOwnProperty('name')) {
      return new Date(value.timestamp).toUTCString() + ',  ' + value.name.substring(0, 7) + ', ' + value.author;
    } else {
      return value;
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.model !== null && this.model !== undefined) {
      this.formControl.setValue(this.model);
    }
    this.filteredOptions = this.formControl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }

  private _filter(value: any): string[] {
    if (this.source === undefined) {
      return [];
    } else if (value === undefined) {
      return this.source;
    }

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
          || new Date(option.timestamp).toUTCString().toLowerCase().includes(filterValue) ||
          filterValue.includes(this.formatValue(option).toLowerCase());
      } else {
        return option.toLowerCase().includes(filterValue);
      }
    });
  }

  ngOnInit(): void {
    if (this.model !== null && this.model !== undefined) {
      this.formControl.setValue(this.model);
    }
    this.filteredOptions = this.formControl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }
}
