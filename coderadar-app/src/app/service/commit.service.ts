import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppConfig} from '../AppConfig';
import {ICommitsGetResponse} from '../city-map/interfaces/ICommitsGetResponse';

@Injectable()
export class CommitService {

    constructor(private http: HttpClient) {
    }

    loadCommits(): Observable<ICommitsGetResponse> {
      return this.http.get<ICommitsGetResponse>(`${AppConfig.BASE_URL}/projects/7/commits`);
    }

}
