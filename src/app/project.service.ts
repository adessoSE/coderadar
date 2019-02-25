import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
import {Project} from './project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private httpClient: HttpClient) { }

  private apiURL = 'http://localhost:8080/';

  public addProject(project: Project) {
    return this.httpClient.post<Project>(this.apiURL + 'projects', JSON.stringify(project), {headers: new HttpHeaders()
        .set('Content-Type', 'application/json'), observe: 'response'});
  }
}
