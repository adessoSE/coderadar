

import { Component, OnInit } from '@angular/core';
import { HttpfetcherService } from '../services/httpfetcher.service';
import { Subscription } from 'rxjs';
import { MetricValuesTreeResponse } from '../interfaces/metricValuesTreeResponse';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  sideNavOpened = true;
  // Attributes for the head monopoly widgets
  headMonopolyFrequencyOfChange: MetricValuesTreeResponse;
  headMonopolyMultipleAuthorResult: MetricValuesTreeResponse;
  headMonopolySingleAuthorKomplexityResult: MetricValuesTreeResponse;

  public fetchedData: Subscription;

  constructor(private service: HttpfetcherService) { }

  ngOnInit() {
    //Head monopoly widget - frequency of change
    this.fetchedData = this.service.getHeadMonopolyFrequencyOfChange().subscribe(
      (resp: MetricValuesTreeResponse) => {
        this.headMonopolyFrequencyOfChange = resp;
      }
    );
    //Head monopoly widget - multiple author
    this.fetchedData = this.service.getHeadMonopolyMultipleAuthor().subscribe(
      (resp: MetricValuesTreeResponse) => {
        this.headMonopolyMultipleAuthorResult = resp;
      }
    );
    //Head monopoly widget - single author and max komplexity
    this.fetchedData = this.service.getHeadMonopolySingleAuthorOfFile().subscribe(
      (resp: MetricValuesTreeResponse) => {
        this.headMonopolySingleAuthorKomplexityResult = resp;
      }
    );
  }
}
