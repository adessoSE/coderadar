import {AfterViewInit, Component, ElementRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {DependencyTreeProvider} from '../DependencyTreeProvider';
import { afterLoad } from '../../../assets/js/dependency-tree';

@Component({
  selector: 'app-tree-root',
  templateUrl: './dependency-root.component.html',
  styleUrls: ['./dependency-root.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DependencyRootComponent implements AfterViewInit {
  @ViewChild('3input') input: ElementRef;
  node: Node;

  constructor(dependencyTreeProvider: DependencyTreeProvider) {
    this.node = dependencyTreeProvider.getDependencyTree();
  }

  ngAfterViewInit(): void {
    this.input.nativeElement.value = JSON.stringify(this.node);
    afterLoad();
  }
}
