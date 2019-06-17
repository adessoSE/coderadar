import {AfterViewInit, Component, ElementRef, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import { afterLoad } from '../../../assets/js/dependency-tree';
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../service/project.service";

@Component({
  selector: 'app-tree-root',
  templateUrl: './dependency-root.component.html',
  styleUrls: ['./dependency-root.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DependencyRootComponent implements AfterViewInit, OnInit {
  @ViewChild('3input') input: ElementRef;
  node: any;
  projectId: number;
  commitName: any;

  constructor(private router: Router, private projectService: ProjectService, private route: ActivatedRoute) {
    this.node = projectService.getDependencyTree(this.projectId, this.commitName);
  }

  ngOnInit():void {
    this.route.params.subscribe(params => {
      this.projectId = params.projectId;
      this.commitName = params.commitName;
    });
  }

  ngAfterViewInit(): void {
    this.input.nativeElement.value = JSON.stringify(this.node);
    afterLoad(this.node);
  }
}
