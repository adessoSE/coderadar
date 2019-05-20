import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class DependencyTreeProvider {

  private node: any = null;

  constructor(private http: HttpClient) {

  }

  public getDependencyTree(): any {
    return this.node;
  }

  load() {
    return new Promise((resolve, reject) => {
      this.http
        .get<any>('http://localhost:8082/getTree')
        .subscribe(res => {
          this.node = res;
          resolve(true);
        });
    });
  }
}
