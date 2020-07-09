import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {TooltipService} from '../../service/tooltip.service';
import {map} from 'rxjs/operators';
import {VisualizationConfig} from '../../VisualizationConfig';
import {Observable} from 'rxjs';
import {ScreenType} from "../../enum/ScreenType";

@Component({
  selector: 'app-tooltip',
  templateUrl: './tooltip.component.html',
  styleUrls: ['./tooltip.component.scss']
})
export class TooltipComponent implements OnInit {

  @ViewChild("tooltip_div")
  tooltipRef: ElementRef;
  tooltipElement: HTMLElement;

  @Input()
  screenType: ScreenType;

  content$: Observable<{ elementName: string, metrics: any }>;

  constructor(private tooltipService: TooltipService) {

  }

  ngOnInit() {
    this.tooltipElement = this.tooltipRef.nativeElement;


    this.content$ = this.tooltipService.getTooltipControls(this.screenType).content$
      .pipe(
        map((tooltipObject) => {
          const readableMetrics = {};

          if (tooltipObject.metrics) {
            Object.keys(tooltipObject.metrics).map((key) => {
              readableMetrics[VisualizationConfig.getShortNameByMetricName(key).shortName] = tooltipObject.metrics[key];
            });
          }
          // Hack: Add a zero width space character so css can wrap the elementName
          const partitionedName = tooltipObject.elementName.replace(new RegExp('/', 'g'), '/\u200b');

          return {
            elementName: partitionedName,
            metrics: readableMetrics
          };
        })
      );

    this.tooltipService.getTooltipControls(this.screenType).hide$.subscribe(() => {
      this.hide();
    });

    this.tooltipService.getTooltipControls(this.screenType).show$.subscribe(() => {
      this.show();
    });

    this.tooltipService.getTooltipControls(this.screenType).position$.subscribe((position) => {
      this.followPosition(position);
    });

  }

  followPosition(position: { x: number, y: number }) {
    this.tooltipElement.style.left = position.x - this.tooltipElement.offsetWidth / 2 + 'px';
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
