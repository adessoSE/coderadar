import {Component, ElementRef, ViewChild} from '@angular/core';
import {DependencyTreeProvider} from '../DependencyTreeProvider';
import {Globals} from '../Globals';

@Component({
  selector: 'app-tree-root',
  templateUrl: './dependency-root.component.html',
  styleUrls: ['./dependency-root.component.scss']
})
export class DependencyRootComponent {
  @ViewChild('canvas') canvasElement: ElementRef;
  @ViewChild('activeDependency') activeDependency: ElementRef;
  @ViewChild('dependencyTree') dependencyTree: ElementRef;
  @ViewChild('canvasContainer') canvasContainer: ElementRef;
  node: Node;

  constructor(dependencyTreeProvider: DependencyTreeProvider, private global: Globals) {
    this.node = dependencyTreeProvider.getDependencyTree();
  }

  toggle(currentNode): Promise<string> {
    return new Promise((resolve, reject) => {
      currentNode.expanded = !currentNode.expanded;
      if (this.global.activeDependency === currentNode) {
        this.global.activeDependency = undefined;
      }
      if (!currentNode.expanded) {
        this.contractChildren(currentNode);
      }
      resolve('success');
    });
  }

  toggleDependency(currentNode): Promise<string> {
    return new Promise((resolve, reject) => {
      currentNode.expanded = !currentNode.expanded;
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
      resolve('success');
    });
  }

  toggleOnEvent(event) {
    this.toggle(event).then(res => this.renderOnEvent());
  }

  toggleDependencyOnEvent(event) {
    this.toggleDependency(event);
    // this.renderOnEvent();
  }

  renderOnEvent() {
    console.log('call rerender');
    this.global.dependencyRoot = this.dependencyTree.nativeElement;
    this.global.rootOffset = this.canvasElement.nativeElement.offsetTop;
    // console.log(event + ', ' + this.canvasElement.nativeElement.offsetHeight);

    const ctx = this.canvasElement.nativeElement.getContext('2d');
    ctx.canvas.height = this.canvasContainer.nativeElement.offsetHeight;
    ctx.canvas.width = this.canvasElement.nativeElement.offsetWidth;
    if (!this.global.activeDependency) {
      this.activeDependency.nativeElement.innerText = "Choose File...";
      ctx.clearRect(0, 0, this.canvasElement.nativeElement.width, this.canvasElement.nativeElement.height);
    } else {
      ctx.clearRect(0, 0, this.canvasElement.nativeElement.width, this.canvasElement.nativeElement.height);
      this.activeDependency.nativeElement.innerText = this.global.activeDependency.filename;
      this.listDependencies(this.global.activeDependency, ctx, 0);
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
