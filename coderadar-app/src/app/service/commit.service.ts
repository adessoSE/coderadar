import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ICommitsGetResponse} from '../interfaces/ICommitsGetResponse';
import {Observable} from 'rxjs';
import {AppConfig} from '../AppConfig';

@Injectable()
export class CommitService {

    constructor(private http: HttpClient) {
    }

    loadCommits(): Observable<ICommitsGetResponse> {
      return this.http.get<ICommitsGetResponse>(`${AppConfig.BASE_URL}/projects/1/commits`);
    }

}
