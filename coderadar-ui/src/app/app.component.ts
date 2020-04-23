import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  private static apiUrl = 'http://localhost:8080/api/';

  title = 'coderadar';

  public static getApiUrl() {
    return this.apiUrl;
  }

  static trimProjectName(name: string) {
    if (name !== undefined && name !== null) {
      if (name.length > 50) {
        let result = name.substr(0, 50);
        result += '...';
        return result;
      }
      return name;
    } else {
      return '';
    }
  }

  static trimProjectNameToLength(name: string, length: number) {
    if (name !== undefined && name !== null) {
      if (name.length > length) {
        let result = name.substr(0, length);
        result += '...';
        return result;
      }
      return name;
    } else {
      return '';
    }
  }
}
