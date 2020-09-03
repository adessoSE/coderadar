import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {ScreenType} from '../enum/ScreenType';
import {ScreenComponent} from '../visualization/screen/screen.component';

export class TooltipControls {

  content$: Observable<{elementName: string, metrics: any}>;
  show$: Observable<unknown>;
  hide$: Observable<unknown>;
  position$: Observable<{x: number, y: number}>;

  constructor(public screen: ScreenComponent,
              public content: Subject<{elementName: string, metrics: any}>,
              public show: Subject<unknown>,
              public hide: Subject<unknown>,
              public position: Subject<{x: number, y: number}>) {
    this.content$ = this.content.asObservable();
    this.show$ = this.show.asObservable();
    this.hide$ = this.hide.asObservable();
    this.position$ = this.position.asObservable();

  }
}

@Injectable()
export class TooltipService {

  private tooltips: { [id: string]: TooltipControls} = {};

  constructor() {
  }

  setContent(tooltipObject: { elementName: string, metrics: any }, screenType: ScreenType) {
    this.tooltips[screenType.toString()].content.next(tooltipObject);
  }

  setMousePosition(position: { x: number, y: number }, screenType: ScreenType) {
    this.tooltips[screenType.toString()].position.next(position);
  }

  hide(screenType: ScreenType) {
    if (screenType) {
      this.tooltips[screenType.toString()].hide.next();
    } else {
      for (const key in ScreenType) {
        if (this.tooltips[key]) {this.tooltips[key.toString()].hide.next(); }
      }
    }

  }

  show(screenType: ScreenType) {
    if (screenType) {
      this.tooltips[screenType.toString()].show.next();
    } else {
      for (const key in ScreenType) {
        if (this.tooltips[key]) {this.tooltips[key.toString()].show.next(); }
      }
    }
  }

  public getTooltipControls(screenType: ScreenType): TooltipControls {
    return this.tooltips[screenType.toString()];
  }

  addScreen(screen: ScreenComponent) {
    if (this.tooltips[screen.toString()]) { return; }
    this.tooltips[screen.screenType.toString()] = new TooltipControls(
      screen,
      new Subject<{ elementName: string, metrics: any }>(),
      new Subject(),
      new Subject(),
      new Subject<{ x: number, y: number }>()
    );
  }


}




