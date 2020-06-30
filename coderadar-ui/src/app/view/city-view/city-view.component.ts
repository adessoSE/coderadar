import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AppEffects} from '../../city-map/shared/effects';
import {Title} from '@angular/platform-browser';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {ProjectService} from '../../service/project.service';
import {UserService} from '../../service/user.service';
import {AppComponent} from '../../app.component';
import {Project} from '../../model/project';

@Component({
  selector: 'app-city-view',
  templateUrl: './city-view.component.html',
  styleUrls: ['./city-view.component.css'],
})
export class CityViewComponent implements OnInit {

  appComponent = AppComponent;
  project: Project;
  projectId: number;

  constructor(private projectService: ProjectService, private route: ActivatedRoute,
              private cityEffects: AppEffects, private titleService: Title,
              private userService: UserService, private router: Router) {
    this.project = new Project();
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.cityEffects.currentProjectId = params.id;
      this.projectId = params.id;
      this.setTitle();
    });
  }

  private setTitle() {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.project = new Project(response.body);
        this.titleService.setTitle('Coderadar - ' + AppComponent.trimProjectName(this.project.name) + ' - 3D view');
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.setTitle());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }
}
