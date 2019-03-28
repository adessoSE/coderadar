import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  private static apiUrl = 'http://localhost:8080/';

  title = 'coderadar';

  public static getApiUrl() {
    return this.apiUrl;
  }
}
