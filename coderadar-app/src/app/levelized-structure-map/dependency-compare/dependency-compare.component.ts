import {AfterViewInit, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {ProjectService} from "../../service/project.service";
import {FORBIDDEN} from "http-status-codes";
import {afterCompareLoad} from "../../../assets/js/compare-tree";

@Component({
  selector: 'app-dependency-compare',
  templateUrl: './dependency-compare.component.html',
  styleUrls: ['./dependency-compare.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DependencyCompareComponent implements AfterViewInit {
  node: any;
  projectId: number;
  commitName1: any;
  commitName2: any;

  constructor(private router: Router, private userService: UserService,
              private projectService: ProjectService, private route: ActivatedRoute) {
  }

  getData(): void {
    this.projectService.getCompareTree(this.projectId, this.commitName1, this.commitName2).then(response => {
      this.node = response.body;
      afterCompareLoad(this.node);
      // TODO action listener aufrufen
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
      this.commitName1 = params.commitName1;
      this.commitName2 = params.commitName2;
      this.getData();
    });
  }
}
