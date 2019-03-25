import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ICommitsGetResponse} from '../interfaces/ICommitsGetResponse';
import {AppComponent} from '../../app.component';

@Injectable()
export class CommitService {

    constructor(private http: HttpClient) {
    }

    loadCommits(projectId: number): Observable<ICommitsGetResponse> {
      return this.http.get<ICommitsGetResponse>(`${AppComponent.getApiUrl()}projects/${projectId}/commits`);
    }

}
