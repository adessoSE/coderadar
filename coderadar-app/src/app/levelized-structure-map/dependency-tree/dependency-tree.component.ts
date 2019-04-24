import {Component, DoCheck, ElementRef, Input, ViewChild} from '@angular/core';
import {Globals} from '../Globals';

@Component({
  selector: 'app-tree-node',
  templateUrl: './dependency-tree.component.html',
  styleUrls: ['./dependency-tree.component.scss']
})
export class DependencyTreeComponent implements DoCheck {
  @Input() node: Node;
  @Input() canvasElement: any;

  constructor(private global: Globals) {}

  ngDoCheck() {
    this.global.dependencyRoot = document.getElementById('dependencyTree') as HTMLElement;
    this.global.rootOffset = document.getElementById('structure-map-header').offsetHeight;

    console.log(document.getElementById('activeDependency').offsetHeight + document.getElementById('dependencyTree').offsetHeight);

    const ctx = this.canvasElement.getContext('2d');
    // ctx.canvas.height = document.body.offsetHeight - document.getElementById('structure-map-header').offsetHeight;
    ctx.canvas.height = document.getElementById('activeDependency').offsetHeight + document.getElementById('dependencyTree').offsetHeight;
    ctx.canvas.width = this.global.dependencyRoot.offsetWidth;
    if (this.global.activeDependency) {
      ctx.clearRect(0, 0, this.canvasElement.width, this.canvasElement.height);
      this.listDependencies(this.global.activeDependency, ctx, 0);
    } else {
      ctx.clearRect(0, 0, this.canvasElement.width, this.canvasElement.height);
    }
  }

  toggle(currentNode): Node {
    currentNode.expanded = !currentNode.expanded;
    if (this.global.activeDependency === currentNode) {
      this.global.activeDependency = undefined;
    }
    if (!currentNode.expanded) {
      this.contractChildren(currentNode);
    }
    return currentNode;
  }

  toggleDependency(currentNode) {
    currentNode = this.toggle(currentNode);
    if (this.global.activeDependency) {
      this.findLastHTMLElement(this.global.activeDependency).classList.remove('activeDependency');
      this.global.activeDependency.expanded = false;
    }
    if (currentNode.expanded) {
      this.global.activeDependency = currentNode;
      this.findLastHTMLElement(currentNode).classList.add('activeDependency');
    } else {
      this.global.activeDependency = undefined;
    }
  }

  listDependencies(currentNode, ctx, redValue) {
    ctx.lineWidth = 3;
    // draw arrows to my dependencies and call this function for my dependencies
    if (currentNode.dependencies.length > 0) {
      currentNode.dependencies.forEach(dependency => {
        //   find start for arrow
        let start;
        //   find end for arrow
        const end = this.findLastHTMLElement(dependency);
        ctx.strokeStyle = `rgb(${redValue}, 0, 0)`;
        // draw arrow to dependency
        //   draw from start to end
        if (currentNode === this.global.activeDependency) {
          start = document.getElementById('activeDependency');
          this.canvasArrow(ctx, start.offsetLeft + start.offsetWidth, start.offsetTop - this.global.rootOffset,
            end.offsetLeft + end.offsetWidth, end.offsetTop - this.global.rootOffset);
        } else {
          start = this.findLastHTMLElement(currentNode);
          this.canvasArrow(ctx, start.offsetLeft, start.offsetTop - this.global.rootOffset,
            end.offsetLeft + end.offsetWidth, end.offsetTop - this.global.rootOffset);
        }
        // call this function for dependency
        if (dependency.dependencies.length > 0) {
          this.listDependencies(dependency, ctx, redValue + 40);
        }
      });
    }
  }

  findLastHTMLElement(node): HTMLElement {
    let packageName = node.packageName;
    let element;
    while (element === undefined) {
      if (!(document.getElementById(packageName) as HTMLElement)) {
        packageName = packageName.substring(0, packageName.lastIndexOf('.'));
      } else {
        element = document.getElementById(packageName) as HTMLElement;
      }
    }
    return element;
  }

  canvasArrow(context, fromx, fromy, tox, toy) {
    const headlen = 10;
    const angle = Math.atan2(toy - fromy, tox - fromx);
    // draw line
    context.beginPath();
    context.setLineDash([10]);
    context.moveTo(fromx, fromy);
    context.lineTo(tox, toy);
    context.stroke();

    // draw arrow head
    context.beginPath();
    context.moveTo(tox, toy);
    context.setLineDash([0]);
    context.lineTo(tox - headlen * Math.cos(angle - Math.PI / 6), toy - headlen * Math.sin(angle - Math.PI / 6));
    context.moveTo(tox, toy);
    context.lineTo(tox - headlen * Math.cos(angle + Math.PI / 6), toy - headlen * Math.sin(angle + Math.PI / 6));
    context.stroke();
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
