import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Title} from '@angular/platform-browser';
import {Project} from '../../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {ProjectService} from '../../service/project.service';
import {UserService} from '../../service/user.service';
import {AppComponent} from '../../app.component';

@Component({
  selector: 'app-city-view',
  templateUrl: './city-view.component.html',
  styleUrls: ['./city-view.component.scss']
})
export class CityViewComponent implements OnInit {

  public projectId: number;
  project: Project;


  constructor(private projectService: ProjectService, private route: ActivatedRoute,
              private titleService: Title, private userService: UserService, private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.projectId = params.id;
      this.getProject();
    });
  }

  /**
   * Gets the project from the service and saves it in this.project
   */
  private getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.titleService.setTitle('Coderadar - ' + AppComponent.trimProjectName(response.body.name) + ' - 3D view');
        this.project = new Project(response.body);
        this.projectId = this.project.id;
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh().then(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }
}
