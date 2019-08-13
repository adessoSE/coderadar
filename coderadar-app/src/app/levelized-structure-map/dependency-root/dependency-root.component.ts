import {AfterViewInit, Component, ViewEncapsulation} from '@angular/core';
import { afterLoad } from '../../../assets/js/dependency-tree';
import {ActivatedRoute, Router} from '@angular/router';
import {ProjectService} from '../../service/project.service';
import {FORBIDDEN} from 'http-status-codes';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-tree-root',
  templateUrl: './dependency-root.component.html',
  styleUrls: ['./dependency-root.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DependencyRootComponent implements AfterViewInit {
  node: any;
  projectId: number;
  commitName: any;

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
  }

  getData(): void {
    this.projectService.getDependencyTree(this.projectId, this.commitName).then(response => {
      this.node = response.body;
      afterLoad(this.node);
    })
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh().then( (() => this.getData()));
        }
      });
  }

  ngAfterViewInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.commitName = params.commitName;
      this.getData();
    });
  }
}
