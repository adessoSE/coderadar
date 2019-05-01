import {Component, Input} from '@angular/core';
import {
  trigger,
  state,
  style,
  animate,
  transition,
  query,
} from '@angular/animations';

@Component({
  selector: 'app-speed-dial',
  templateUrl: './speed-dial.component.html',
  styleUrls: [ './speed-dial.component.scss' ],
  animations: [
    trigger('spinInOut', [
      state('in', style({transform: 'rotate(0)', opacity: '1'})),
      transition(':enter', [
        style({transform: 'rotate(-180deg)', opacity: '0'}),
        animate('150ms ease')
      ]),
      transition(':leave', [
        animate('150ms ease', style({transform: 'rotate(180deg)', opacity: '0'}))
      ]),
    ]),
    trigger('preventInitialAnimation', [
      transition(':enter', [
        query(':enter', [], {optional: true})
      ]),
    ]),
  ],
})
export class SpeedDialComponent {
  @Input() icon: string;
  @Input() actions = [];
}
