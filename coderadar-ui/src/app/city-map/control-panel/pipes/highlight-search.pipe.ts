import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'highlightSearch'
})
export class HighlightSearchPipe implements PipeTransform {

  transform(value: any, args?: string): any {
    if(!args) {return value;}
    //if(!(args instanceof string)) return value;
    if(args.toString()===" ") return value;
    var re = new RegExp(args,'gi');
    return value.replace(re,"<mark>$&</mark>");
  }

}
