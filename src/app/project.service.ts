import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Project} from './project';
import {Router} from '@angular/router';
import {UserService} from './user.service';
import {User} from './user';

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
}
