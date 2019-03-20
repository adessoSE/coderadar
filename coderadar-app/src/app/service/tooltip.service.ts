import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable()
export class TooltipService {

    private tooltipSource = new Subject<{elementName: string, metrics: any}>();
    private showTooltipSource = new Subject();
    private hideTooltipSource = new Subject();
    private trackPositionSource = new Subject<{x: number, y: number}>();

    tooltipContent$ = this.tooltipSource.asObservable();
    showTooltip$ = this.showTooltipSource.asObservable();
    hideTooltip$ = this.hideTooltipSource.asObservable();
    trackPosition$ = this.trackPositionSource.asObservable();

    constructor() {
    }

    setContent(tooltipObject: {elementName: string, metrics: any}) {
        this.tooltipSource.next(tooltipObject);
    }

    setMousePosition(position: {x: number, y: number}) {
        this.trackPositionSource.next(position);
    }

    hide() {
        this.hideTooltipSource.next();
    }

    show() {
        this.showTooltipSource.next();
    }

}
