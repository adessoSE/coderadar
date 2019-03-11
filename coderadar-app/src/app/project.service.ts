import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Project} from './project';
import {Router} from '@angular/router';
import {UserService} from './user.service';
import {FilePatterns} from './file-patterns';
import {AnalyzerConfiguration} from './analyzer-configuration';
import {Commit} from './commit';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private userService: UserService, private router: Router, private httpClient: HttpClient) { }

  private apiURL = 'http://localhost:8080/';

  public addProject(project: Project) {
    return this.httpClient.post<Project>(this.apiURL + 'projects', JSON.stringify(project), {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getProject(id: number) {
    return this.httpClient.get<Project>(this.apiURL + 'projects/' + id , {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getProjectFilePatterns(id: number) {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/files', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getProjectModules(id: number) {
    return this.httpClient.get<any[]>(this.apiURL + 'projects/' + id + '/modules', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getProjects() {
    return this.httpClient.get<Project[]>(this.apiURL + 'projects', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public deleteProject(id: number) {
    return this.httpClient.delete(this.apiURL + 'projects/' + id, {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public setProjectFilePatterns(id: number, filePatternss: FilePatterns[]) {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/files', { filePatterns: filePatternss},
      {headers: new HttpHeaders().set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public addProjectModule(id: number, module: string) {
      return this.httpClient.post(this.apiURL + 'projects/' + id + '/modules', {modulePath: module}, {headers: new HttpHeaders()
          .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public addAnalyzerConfigurationToProject(id: number, analyzer: AnalyzerConfiguration) {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyzers', JSON.stringify(analyzer), {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public editAnalyzerConfigurationForProject(id: number, analyzer: AnalyzerConfiguration) {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyzers/' + analyzer.id, JSON.stringify(analyzer),
      {headers: new HttpHeaders().set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getAnalyzers() {
    return this.httpClient.get<AnalyzerConfiguration[]>(this.apiURL + 'analyzers', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public startAnalyzingJob(id: number) {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyzingJob', {fromDate: 0, active: true, rescan: true},
      {headers: new HttpHeaders().set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getAnalyzingJob(id: number) {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/analyzingJob', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getProjectAnalyzers(id: number) {
    return this.httpClient.get<any>(this.apiURL + 'projects/' + id + '/analyzers', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public editProject(project: Project) {
    return this.httpClient.post<Project>(this.apiURL + 'projects/' + project.id, JSON.stringify(project),
      {headers: new HttpHeaders().set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getCommits(id: number) {
    return this.httpClient.get<Commit[]>(this.apiURL + 'projects/' + id + '/commits', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getCommitsMetricValues(id: number, commitName: string, metricsNames: string[]) {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/metricvalues/perCommit',
      {commit: commitName, metrics: metricsNames}, {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }
}
