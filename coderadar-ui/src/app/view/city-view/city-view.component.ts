import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AppEffects} from '../../city-map/shared/effects';
import {Title} from '@angular/platform-browser';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {ProjectService} from '../../service/project.service';
import {UserService} from '../../service/user.service';
import {AppComponent} from '../../app.component';

@Component({
  selector: 'app-city-view',
  templateUrl: './city-view.component.html',
})
export class CityViewComponent implements OnInit {

  constructor(private projectService: ProjectService, private route: ActivatedRoute,
              private cityEffects: AppEffects,  private titleService: Title,
              private userService: UserService, private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.cityEffects.currentProjectId = params.id;
      this.setTitle(params.id);
    });
  }

  private setTitle(id: number) {
    this.projectService.getProject(id)
      .then(response => {
        this.titleService.setTitle('Coderadar - ' + AppComponent.trimProjectName(response.body.name) + ' - 3D view');
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.setTitle(id));
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }
}
