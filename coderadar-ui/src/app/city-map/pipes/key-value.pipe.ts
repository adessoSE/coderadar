import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'keyValue',
  pure: false
})
export class KeyValuePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (!value) {
      return undefined;
    }

    const keys = [];
    for (const key in value) {
      if (value.hasOwnProperty(key)) {
        keys.push({key, value: value[key]});
      }
    }
    return keys;
  }

}
