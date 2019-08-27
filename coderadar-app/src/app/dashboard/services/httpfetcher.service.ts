import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpfetcherService {
  constructor(private http: HttpClient) { }
  configUrl = 'assets/config.json';

  sonarQubeExampleUrl = 'https://next.sonarqube.com/sonarqube/api/measures/component?component=org.sonarsource.java:java';

  getConfig() {
    return this.http.get(this.configUrl);
  }

  getMetricsFromSite(metricKeys) {
    return this.http.get(this.sonarQubeExampleUrl + '&metricKeys=' + metricKeys);
  }

  getIssues() {
    return this.http.get('assets/issues.json');
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
