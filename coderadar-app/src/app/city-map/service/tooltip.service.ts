import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable()
export class TooltipService {

  private tooltipSource = new Subject<{ elementName: string, metrics: any }>();
  tooltipContent$ = this.tooltipSource.asObservable();
  private showTooltipSource = new Subject();
  showTooltip$ = this.showTooltipSource.asObservable();
  private hideTooltipSource = new Subject();
  hideTooltip$ = this.hideTooltipSource.asObservable();
  private trackPositionSource = new Subject<{ x: number, y: number }>();
  trackPosition$ = this.trackPositionSource.asObservable();

  constructor() {
  }

  setContent(tooltipObject: { elementName: string, metrics: any }) {
    this.tooltipSource.next(tooltipObject);
  }

  setMousePosition(position: { x: number, y: number }) {
    this.trackPositionSource.next(position);
  }

  hide() {
    this.hideTooltipSource.next();
  }

  show() {
    this.showTooltipSource.next();
  }

}
