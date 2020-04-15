import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {AppComponent} from '../app.component';
import {Contributor} from '../model/contributor';

@Injectable({
  providedIn: 'root'
})
export class ContributorService {

  private apiURL = AppComponent.getApiUrl();

  constructor(private httpClient: HttpClient) {
  }

  getContributorsForProject(projectId: number): Promise<HttpResponse<Contributor[]>> {
    return this.httpClient.get<Contributor[]>(this.apiURL + 'projects/' + projectId + '/contributors',
      {observe: 'response'}).toPromise();
  }

  getContributorsForFile(projectId: number, path: string, commitHash: string): Promise<HttpResponse<Contributor[]>> {
    return this.httpClient.post<Contributor[]>(this.apiURL + 'projects/' + projectId + '/contributors/path',
      {path, commitHash}, {observe: 'response'}).toPromise();
  }

  mergeContributors(contributors: Contributor[], displayName: string): Promise<HttpResponse<any>> {
    return this.httpClient.post<any>(this.apiURL + 'contributors/merge',
      {contributorIds: contributors.map(value => value.id), displayName},
      {observe: 'response'}).toPromise();
  }

}
