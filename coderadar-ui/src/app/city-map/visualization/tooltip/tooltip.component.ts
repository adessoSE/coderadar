import {Component, OnInit} from '@angular/core';
import {TooltipService} from '../../service/tooltip.service';
import {map} from 'rxjs/operators';
import {VisualizationConfig} from '../../VisualizationConfig';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-tooltip',
  templateUrl: './tooltip.component.html',
  styleUrls: ['./tooltip.component.scss']
})
export class TooltipComponent implements OnInit {

  tooltipElement: HTMLElement;

  content$: Observable<{ elementName: string, metrics: any }>;

  constructor(private tooltipService: TooltipService) {
  }

  ngOnInit() {
    this.tooltipElement = document.querySelector('#tooltip') as HTMLElement;

    this.content$ = this.tooltipService.tooltipContent$
      .pipe(
        map((tooltipObject) => {
          const readableMetrics = {};

          if (tooltipObject.metrics) {
            Object.keys(tooltipObject.metrics).map((key) => {
              readableMetrics[VisualizationConfig.getShortNameByMetricName(key).shortName] = tooltipObject.metrics[key];
            });
          }
          //Hack: Add a zero width space character so css can wrap the elementName
          var partitionedName = tooltipObject.elementName.replace(new RegExp("/","g"),"/\u200b");

          return {
            elementName: partitionedName,
            metrics: readableMetrics
          };
        })
      );

    this.tooltipService.hideTooltip$.subscribe(() => {
      this.hide();
    });

    this.tooltipService.showTooltip$.subscribe(() => {
      this.show();
    });

    this.tooltipService.trackPosition$.subscribe((position) => {
      this.followPosition(position);
    });

  }

  followPosition(position: { x: number, y: number }) {
    // this.tooltipElement.style.left = position.x + 15 + 'px';
    // this.tooltipElement.style.top = position.y + 15 + 'px';
    this.tooltipElement.style.left = position.x - this.tooltipElement.offsetWidth/2 + 'px';
    this.tooltipElement.style.top = position.y - this.tooltipElement.offsetHeight + 'px';
  }

  show() {
    this.tooltipElement.classList.add('visible');
  }

  hide() {
    this.tooltipElement.classList.remove('visible');
    this.tooltipElement.style.left = '-1000px';
    this.tooltipElement.style.top = '-1000px';
  }
}
