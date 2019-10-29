import {AfterViewInit, Component, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ProjectService} from '../../service/project.service';
import {FORBIDDEN} from 'http-status-codes';
import {UserService} from '../../service/user.service';
import {DependencyBase} from '../dependency-base';

@Component({
  selector: 'app-tree-root',
  templateUrl: './dependency-root.component.html',
  styleUrls: ['./dependency-root.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DependencyRootComponent extends DependencyBase implements AfterViewInit {

  constructor(private router: Router, private userService: UserService, private projectService: ProjectService,
              private route: ActivatedRoute) {
    super();
  }

  ngAfterViewInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.commitName = params.commitName;
      this.getData();
    });
  }

  getData(): void {
    this.projectService.getDependencyTree(this.projectId, this.commitName).then(response => {
      this.node = response.body;
      this.ctx = (this.canvas.nativeElement as HTMLCanvasElement).getContext('2d');
      this.checkDown = this.checkUp = true;
      setTimeout(() => this.draw(() => this.loadDependencies(this.node)), 50);
    })
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh(() => this.getData());
        }
      });
  }
}
