import {AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {ProjectService} from '../../service/project.service';
import {FORBIDDEN} from 'http-status-codes';
import {DependencyBaseComponent} from '../dependency-base/dependency-base.component';

@Component({
  selector: 'app-dependency-compare',
  templateUrl: './dependency-compare.component.html',
  styleUrls: ['./dependency-compare.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DependencyCompareComponent extends DependencyBaseComponent implements OnInit, AfterViewInit {

  commitName2: any;
  @ViewChild('3showChanged') showChangedContainer;

  constructor(router: Router, userService: UserService, projectService: ProjectService, private route: ActivatedRoute) {
    super();
    this.projectService = projectService;
    this.userService = userService;
    this.router = router;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.commitName = params.commitName1;
      this.commitName2 = params.commitName2;
      this.getProject();
    });
  }

  ngAfterViewInit(): void {
    this.getData();
  }

  getData(): void {
    this.projectService.getCompareTree(this.projectId, this.commitName, this.commitName2).then(response => {
      this.node = response.body;
      this.checkChanged = false;
      this.svg = document.getElementById('3svg');
      this.checkDown = this.checkUp = true;
      setTimeout(() => this.draw(() => this.loadDependencies(this.node, this.checkChanged)), 50);
    })
      .catch(e => {
        if (e.status && e.status === FORBIDDEN) {
          this.userService.refresh(() => this.getData());
        }
      });
  }

  public onShowChangedChanged(): void {
    this.checkChanged = !this.checkChanged;
    this.draw(() => this.loadDependencies(this.node, this.checkChanged));
  }
}
