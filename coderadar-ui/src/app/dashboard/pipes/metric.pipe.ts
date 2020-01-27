import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'metricPipe'
})
export class MetricPipe implements PipeTransform {

  transform(value: string, args?: string): string {
    const partArray = value.split('.');
    const lastIndex = partArray.length - 1;
    return partArray[lastIndex];
  }

}
