import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output, ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {Observable} from 'rxjs';
import {FormControl} from '@angular/forms';
import {debounceTime, distinctUntilChanged, map, startWith} from 'rxjs/operators';

@Component({
  selector: 'app-autosuggest-wrapper',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './autosuggest-wrapper.component.html',
  styleUrls: ['./autosuggest-wrapper.component.scss']
})
export class AutosuggestWrapperComponent implements  OnInit{

  @ViewChild('inputElement') inputElement;

  @Input() model$: Observable<any>;
  @Input() source$: Observable<any[]>;
  @Input() isDisabled: boolean;
  @Input() alignRight = false;
  @Input() label: string;
  @Output() valueChanged = new EventEmitter();
  @Input() filterOptions :((value: any,source:any[])=> any[]) = (value, options) => options;
  @Input() formatOption :((value: any)=> string ) = value => value.toString();
  displayOptions: Observable<{value:any,displayValue:string }[]>;
  formattedOptions: {value:any,displayValue:string }[];
  formControl = new FormControl();

  handleValueChanged(selectedOption: any) {
    this.valueChanged.emit(selectedOption.value);
  }

  displayFunction(option:{value:any,displayValue:string }):string{
    if(option){
      return option.displayValue;
    }else{
      return "";
    }
  }

  associateFormattedOptions(value):{value:any,displayValue:string}{
    return {value:value,displayValue:this.formatOption(value)};
  }

  ngOnInit(): void {
    if(this.model$)this.model$.subscribe(value => this.formControl.setValue(this.associateFormattedOptions(value)));
    this.formattedOptions = [];
    this.source$.subscribe(value => value.forEach(option => this.formattedOptions.push(this.associateFormattedOptions(option))));
    this.displayOptions = this.formControl.valueChanges
      .pipe(
        debounceTime(200),
        distinctUntilChanged(),
        startWith(' '),
        map(value => this.filterOptions(value,this.formattedOptions))
      );
  }
}

