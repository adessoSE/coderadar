import {Component, Input, OnInit} from '@angular/core';
import {DependencyRootComponent} from '../dependency-root/dependency-root.component';

@Component({
  selector: 'app-tree-node',
  templateUrl: './tree-node.component.html',
  styleUrls: ['./tree-node.component.scss']
})
export class TreeNodeComponent implements OnInit {

  level = 0;
  timer: any;
  preventSimpleClick: boolean;
  @Input() node: any;
  @Input() root: DependencyRootComponent;
  @Input() hasSiblings: boolean;

  constructor() {
  }

  ngOnInit() {
  }

  setLevel(level: number): void {
    this.level = level;
  }

  onClick(event): void {
    event.stopPropagation();
    this.timer = setTimeout(() => {
      if (!this.preventSimpleClick) {
        if (this.root.activeDependency === event.target) {
          this.root.activeDependency = undefined;
          this.root.activeDependencyContainer.nativeElement.textContent = 'No active dependency chosen.';
        } else {
          this.root.activeDependency = event.target;
          this.root.activeDependencyContainer.nativeElement.textContent = event.target.textContent;
        }
        // clear and draw arrows for active dependency
        this.root.draw(() => this.root.loadDependencies(this.root.node, this.root.checkChanged));
      }
      this.preventSimpleClick = false;
    }, 300);
  }

  stopSingleClick(event): void {
    event.stopPropagation();
    clearTimeout(this.timer);
    this.preventSimpleClick = true;
  }

  onDBClick(event): void {
    this.stopSingleClick(event);
    this.root.toggle(event.target);
    this.root.draw(() => this.root.loadDependencies(this.root.node, this.root.checkChanged));
  }

  getClassString(currentNode: any): string {
    let classString;
    if (currentNode.children.length > 0) {
      classString = 'package';
    } else if (currentNode.dependencies.length > 0) {
      classString = 'class--dependency';
    } else if (currentNode.children.length === 0 && currentNode.dependencies.length === 0) {
      classString = 'class';
    }
    if (currentNode.changed === 'ADD') {
      classString += ' added';
    } else if (currentNode.changed === 'DELETE') {
      classString += ' deleted';
    }
    return classString;
  }

}
