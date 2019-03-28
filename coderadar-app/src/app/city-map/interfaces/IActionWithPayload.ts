import {Action} from '@ngrx/store';

export interface IActionWithPayload<T> extends Action {
  payload: T;
}
