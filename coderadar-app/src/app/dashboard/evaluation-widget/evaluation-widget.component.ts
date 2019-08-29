import { Component, OnInit } from '@angular/core';
import { ProjectService } from 'src/app/service/project.service';
import { ActivatedRoute } from '@angular/router';
import { DataPoint } from '../interfaces/history';

@Component({
  selector: 'app-evaluation-widget',
  templateUrl: './evaluation-widget.component.html',
  styleUrls: ['./evaluation-widget.component.css']
})
export class EvaluationWidgetComponent implements OnInit {
  projectId;
  // Default metrics
  metrics: string[] = ['coderadar:size:sloc:java'];
  chartLabels: string [] = [];
  chartData: number[] = [];
  dataset;

  constructor(private projectService: ProjectService, private route: ActivatedRoute) { }

  historyMetricObserver = {
    next: (resp: any) => {
      resp.points.forEach((element: DataPoint) => {
        let label = '';
        element.x.forEach(x => {
          label += x;
        });
        this.chartLabels.push(label);
        this.chartData.push(element.y);
      });
    },
    error: (err: any) => console.log(err)
  };

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getHistory(this.metrics[0]);
    });
  }

  private getHistory(metric: string) {
    this.projectService.getHistory([2019, 5, 1], [2019, 8, 1], 'WEEK', metric, this.projectId).subscribe(this.historyMetricObserver);
  }

}
