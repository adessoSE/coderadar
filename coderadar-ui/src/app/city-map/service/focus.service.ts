import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable()
export class FocusService {

  private focusElementSource = new Subject<string>();

  elementFocussed$ = this.focusElementSource.asObservable();

  focusElement(elementName: string) {
    this.focusElementSource.next(elementName);
  }



}
