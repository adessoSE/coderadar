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
    const headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
    headers.append('Authorization', accessToken);
    return this.httpClient.post<Project>(this.apiURL + 'projects', JSON.stringify(project), {headers, observe: 'response'});
  }

  public getProjects(accessToken: string) {
    const headers = new HttpHeaders();
    headers.append('Authorization', accessToken);
    return this.httpClient.get<Project[]>(this.apiURL + 'projects', {headers, observe: 'response'});
  }

  public deleteProject(id: number, accessToken: string) {
    const headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
    headers.append('Authorization', accessToken);
    return this.httpClient.delete(this.apiURL + 'projects/' + id, {headers, observe: 'response'});
  }
}
