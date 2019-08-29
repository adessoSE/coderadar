import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpfetcherService {
  constructor(private http: HttpClient) { }

  /**
   * Testdata for the issue-widget
   * The format is from sonarqube
   * @todo create service to get issues
   */
  getIssues() {
    return this.http.get('assets/data/issues.json');
  }

  /**
   * Testdata for the goal-widget 
   * @todo create service to get issues
   */
  getCoverage() {
    return this.http.get('assets/data/goalResponse.json');
  }

  getHotspotData() {
    return this.http.get('assets/data/hotspotDummyData.json');
  }
}
