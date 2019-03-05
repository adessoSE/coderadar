import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Project} from './project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private httpClient: HttpClient) { }

  private apiURL = 'http://localhost:8080/';

  public addProject(project: Project, accessToken: string) {
    return this.httpClient.post<Project>(this.apiURL + 'projects', JSON.stringify(project), {headers: new HttpHeaders()
        .set('Content-Type', 'application/json').append('Authorization', accessToken), observe: 'response'});
  }

  public getProjects(accessToken: string) {
    return this.httpClient.get<Project[]>(this.apiURL + 'projects', {headers: new HttpHeaders()
        .set('Content-Type', 'application/json').append('Authorization', accessToken), observe: 'response'});
  }

  public deleteProject(id: number, accessToken: string) {
    return this.httpClient.delete(this.apiURL + 'projects/' + id, {headers: new HttpHeaders()
        .set('Content-Type', 'application/json').append('Authorization', accessToken), observe: 'response'});
  }
}
