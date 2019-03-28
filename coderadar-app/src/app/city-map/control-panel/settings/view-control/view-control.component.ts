import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {faClone, faSquare} from '@fortawesome/free-regular-svg-icons';
import {ViewType} from '../../../enum/ViewType';

declare var $: any;

@Component({
  selector: 'app-view-control',
  templateUrl: './view-control.component.html',
  styleUrls: ['./view-control.component.scss']
})
export class ViewControlComponent implements OnInit {

  faSquare = faSquare;
  faClone = faClone;

  viewTypes: any = {
    split: ViewType.SPLIT,
    merged: ViewType.MERGED
  };

  @Input() activeViewType: ViewType;

  @Output() viewTypeChanged = new EventEmitter();

  @ViewChild('splitScreenLabel') splitScreenLabelRef: ElementRef;
  @ViewChild('fullScreenLabel') fullScreenLabelRef: ElementRef;

  constructor() {
  }

  ngOnInit() {
    // $(this.splitScreenLabelRef.nativeElement).tooltip();
    // $(this.fullScreenLabelRef.nativeElement).tooltip();
  }

  changeViewType(value) {
    this.viewTypeChanged.emit(value === 0 ? ViewType.SPLIT : ViewType.MERGED);
  }

}
