import {Component, DoCheck, EventEmitter, Input, Output} from '@angular/core';
import {Node} from "../Node";

@Component({
  selector: 'app-tree-node',
  templateUrl: './dependency-tree.component.html',
  styleUrls: ['./dependency-tree.component.scss']
})
export class DependencyTreeComponent implements DoCheck {
  @Input() node: Node;

  @Output()
  toggleDependencyEmitter = new EventEmitter<Node>();
  @Output()
  toggleEmitter = new EventEmitter<Node>();
  @Output()
  renderEmitter = new EventEmitter<string>();

  ngDoCheck() {
    // this.renderEmitter.emit(`change in ${this.node.filename}`);
  }

  toggle(currentNode) {
    this.toggleEmitter.emit(currentNode);
  }

  toggleDependency(currentNode) {
    this.toggleDependencyEmitter.emit(currentNode);
  }
}
