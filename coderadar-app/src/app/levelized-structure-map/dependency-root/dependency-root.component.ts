import {Component, Input, OnInit} from '@angular/core';
import {DependencyTreeProvider} from '../DependencyTreeProvider';
import {Globals} from '../Globals';

@Component({
  selector: 'app-tree-root',
  templateUrl: './dependency-root.component.html',
  styleUrls: ['./dependency-root.component.scss']
})
export class DependencyRootComponent {
  @Input() canvas: any;
  @Input() node: Node;

  constructor(dependencyTreeProvider: DependencyTreeProvider, private global: Globals) {
    this.node = dependencyTreeProvider.getDependencyTree();
  }

  toggle(currentNode): Node {
    currentNode.expanded = !currentNode.expanded;
    if (!currentNode.expanded) {
      this.contractChildren(currentNode);
    }
    return currentNode;
  }

  contractChildren(currentNode) {
    if (this.global.activeDependency === currentNode) {
      this.global.activeDependency = undefined;
    }
    if (currentNode.children.length > 0) {
      currentNode.children.forEach(child => {
        this.contractChildren(child);
      });
      currentNode.expanded = false;
    } else {
      currentNode.expanded = false;
    }
  }
}
