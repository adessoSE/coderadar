import { Metric } from './../interfaces/metricValuesTreeResponse';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { HttpfetcherService } from '../services/httpfetcher.service';

@Component({
  selector: 'app-architecture-widget',
  templateUrl: './architecture-widget.component.html',
  styleUrls: ['./architecture-widget.component.css']
})
export class ArchitectureWidgetComponent implements OnInit {
  private httpFetchedTodos: Subscription;

  dependenciesTotal;
  cyclicalDependenciesTotal;
  maxCyclicalSize;
  minCyclicalSize;
  averageCyclicalSize;
  errorMessage: string;

  constructor(private http: HttpfetcherService) { }

  ngOnInit() {
    // todo refactoring
    const observer = {
      next: (resp: any) => {
        this.dependenciesTotal = resp.metrics[0].value;
        this.cyclicalDependenciesTotal = resp.metrics[1].value;
        this.maxCyclicalSize = resp.metrics[2].value;
        this.minCyclicalSize = resp.metrics[3].value;
        this.averageCyclicalSize = resp.metrics[4].value;
      },
      error: () => {
        this.errorMessage = 'Some error occured';
      }
    }
    // todo use project service to recieve metrics
    this.http.getArchitectureData().subscribe(observer);
  }

}
