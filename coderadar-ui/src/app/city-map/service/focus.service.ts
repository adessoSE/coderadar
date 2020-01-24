import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable()
export class FocusService {

  private focusElementSource = new Subject<string>();

  elementFocussed$ = this.focusElementSource.asObservable();

  private highlightedElementSource = new Subject<string>();

  elementHighlighted$ = this.highlightedElementSource.asObservable();

  focusElement(elementName: string) {
    this.focusElementSource.next(elementName);
  }

  highlightElement(elementName: string) {
    this.highlightedElementSource.next(elementName);
  }

}
