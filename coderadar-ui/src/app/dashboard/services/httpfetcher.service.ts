import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpfetcherService {
  constructor(private http: HttpClient) { }

  /**
   * Testdata for the architecture widget
   */
  getArchitectureData() {
    return this.http.get('assets/data/architecture.json');
  }

  /**
   * Testdata for the issue-widget
   * The format is from sonarqube
   * @todo create service to get issues
   */
  getIssues() {
    return this.http.get('assets/data/issues.json');
  }

  /**
   * Testdata for the trend widget
   * History data from issue type bug
   */
  getIssueBugHistory() {
    return this.http.get('assets/data/issue/issue-bug-history.json');
  }
    /**
   * Testdata for the trend widget
   * History data from issue type vulnerability
   */
  getIssueVulnerabilityHistory() {
    return this.http.get('assets/data/issue/issue-vulnerability-history.json');
  }

  /**
   * Testdata for the head monopoly widget - frequency of change
   */
  getHeadMonopolyFrequencyOfChange() {
    return this.http.get('assets/data/headmonopol-frequencyOfChange.json');
  }

  /**
   * Testdata for the head monopoly widget - single author
   */
  getHeadMonopolySingleAuthorOfFile() {
    return this.http.get('assets/data/headmonopol-singleAuthor.json');
  }

  /**
   * Testdata for the head monopoly widget - multiple author
   */
  getHeadMonopolyMultipleAuthor(){
    return this.http.get('assets/data/headmonopol-multipleAuthor.json');
  }

  /**
   * Testdata for the todo widget
   */
  getTodos(){
    return this.http.get('assets/data/todos-data.json');
  }

  /**
   * Testdata for the goal-widget
   * @todo create service to get issues
   */
  getGoals() {
    return this.http.get('assets/data/goalResponse.json');
  }

  /**
   * Testdata for the hotspot table widget
   */
  getHotspotData() {
    return this.http.get('assets/data/hotspotDummyData.json');
  }
}
