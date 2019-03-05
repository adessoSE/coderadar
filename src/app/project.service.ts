import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Project} from './project';
import {Router} from '@angular/router';
import {UserService} from './user.service';
import {User} from './user';
import {FilePatterns} from './file-patterns';
import {forEach} from '@angular/router/src/utils/collection';
import {AnalyzerConfiguration} from './analyzer-configuration';

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

  public getProjects() {
    return this.httpClient.get<Project[]>(this.apiURL + 'projects', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public deleteProject(id: number) {
    return this.httpClient.delete(this.apiURL + 'projects/' + id, {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public setProjectFilePatterns(id: number, filePatterns: FilePatterns[]) {
    console.log(JSON.stringify(filePatterns));
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/files', JSON.stringify(filePatterns), {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public addProjectModules(id: number, module: string) {
      return this.httpClient.post(this.apiURL + 'projects/' + id + '/modules', {modulePath: module}, {headers: new HttpHeaders()
          .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public addAnalyzerConfigurationToProject(id: number, analyzer: AnalyzerConfiguration) {
    return this.httpClient.post(this.apiURL + 'projects/' + id + '/analyzers', JSON.stringify(analyzer), {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }

  public getAnalyzers() {
    return this.httpClient.get<AnalyzerConfiguration[]>(this.apiURL + 'analyzers', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'}).toPromise();
  }
}
