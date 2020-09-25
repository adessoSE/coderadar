import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {AppComponent} from '../app.component';
import {ProjectRole} from '../model/project-role';
import {Team} from '../model/team';
import {Project} from '../model/project';
import {ProjectWithRoles} from '../model/project-with-roles';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private apiURL = AppComponent.getApiUrl();

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Gets all the teams.
   * Sends a GET request to /projects
   */
  public listTeams(): Promise<HttpResponse<Team[]>> {
    return this.httpClient.get<Team[]>(this.apiURL + 'teams', {observe: 'response'}).toPromise();
  }

  public addTeamToProject(projectId: number, teamId: number, role: ProjectRole): Promise<HttpResponse<any>> {
    return this.httpClient.post<any>(this.apiURL + 'projects/' + projectId + '/teams/' + teamId, {role},
      {observe: 'response'}).toPromise();
  }

  public addUsersToTeam(teamId: number, userIds: number[]): Promise<HttpResponse<any>> {
    return this.httpClient.post<any>(this.apiURL + 'teams/' + teamId + '/users', {userIds},
      {observe: 'response'}).toPromise();
  }

  public removeUsersFromTeam(teamId: number, userIds: number[]): Promise<HttpResponse<any>> {
    return this.httpClient.post<any>(this.apiURL + 'teams/' + teamId + '/users', {userIds},
      {observe: 'response'}).toPromise();
  }

  public createTeam(name: string, userIds: number[]): Promise<HttpResponse<any>> {
    return this.httpClient.post<any>(this.apiURL + 'teams', {name, userIds},
      {observe: 'response'}).toPromise();
  }

  public deleteTeam(teamId: number): Promise<HttpResponse<any>> {
    return this.httpClient.delete<any>(this.apiURL + 'teams/' + teamId,
      {observe: 'response'}).toPromise();
  }

  public getTeam(teamId: number): Promise<HttpResponse<Team>> {
    return this.httpClient.get<Team>(this.apiURL + 'teams/' + teamId,
      {observe: 'response'}).toPromise();
  }

  public listProjectsForTeam(teamId: number): Promise<HttpResponse<Project[]>> {
    return this.httpClient.get<Project[]>(this.apiURL + 'teams/' + teamId + '/projects',
      {observe: 'response'}).toPromise();
  }

  public listProjectsForTeamWithRolesForUser(teamId: number, userId: number): Promise<HttpResponse<ProjectWithRoles[]>> {
    return this.httpClient.get<ProjectWithRoles[]>(this.apiURL + 'teams/' + teamId + '/' + userId + '/projects',
      {observe: 'response'}).toPromise();
  }

  public listTeamsForProject(projectId: number): Promise<HttpResponse<Team[]>> {
    return this.httpClient.get<Team[]>(this.apiURL + 'projects/' + projectId + '/teams',
      {observe: 'response'}).toPromise();
  }

  public listTeamsForUser(userId: number): Promise<HttpResponse<Team[]>> {
    return this.httpClient.get<Team[]>(this.apiURL + 'users/' + userId + '/teams',
      {observe: 'response'}).toPromise();
  }

  public removeTeamFromProject(projectId: number, teamId: number): Promise<HttpResponse<any>> {
    return this.httpClient.delete<any>(this.apiURL + 'projects/' + projectId + '/teams/' + teamId,
      {observe: 'response'}).toPromise();
  }

  public removeTeam(id: number) {
    return this.httpClient.delete<any>(this.apiURL + 'teams/' + id,
      {observe: 'response'}).toPromise();
  }

  editTeam(teamId: number, name: string, userIds: any) {
    return this.httpClient.post<any>(this.apiURL + 'teams/' + teamId,
      {name, userIds}, {observe: 'response'}).toPromise();
  }
}
