import { Component } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { HttpfetcherService } from '../services/httpfetcher.service';
import { Subscription } from 'rxjs';
import { MetricValuesTreeResponse } from '../interfaces/metricValuesTreeResponse';

@Component({
  selector: 'app-material-dashboard',
  templateUrl: './material-dashboard.component.html',
  styleUrls: ['./material-dashboard.component.css']
})
export class MaterialDashboardComponent {
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

