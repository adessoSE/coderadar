import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpfetcherService {
  constructor(private http: HttpClient) { }
  configUrl = 'assets/config.json';

  getConfig() {
    return this.http.get(this.configUrl);
  }

  /**
   * Testdata for the issue-widget
   * The format is from sonarqube
   * @todo create service to get issues
   */
  getIssues() {
    return this.http.get('assets/data/issues.json');
  }

  getIssuesLeakPeriod() {
    return this.http.get('assets/issuesLeakPeriod.json');
  }

  getCoverageMeasurement() {
    return this.http.get('assets/coverageMeasurement.json');
  }

  getHotspotData(){
    return this.http.get('assets/data/hotspotDummyData.json');
  }
}
