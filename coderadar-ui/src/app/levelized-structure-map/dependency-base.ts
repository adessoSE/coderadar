import {ElementRef, HostListener, ViewChild} from '@angular/core';
import html2canvas from 'html2canvas';
import * as $ from 'jquery';
import * as jspdf from 'jspdf';

export abstract class DependencyBase {
  node: any;
  projectId: number;
  commitName: any;
  ctx: any;
  checkDown: boolean;
  checkChanged: boolean;
  checkUp: boolean;
  activeDependency: any;
  formats = [
    { format: 'image/png', value: 'png' },
    { format: 'image/jpeg', value: 'jpg' },
    { format: 'application/pdf', value: 'pdf' }
  ];
  selected = 0;
  @ViewChild('3dependencyTree') div;
  @ViewChild('3activeDependency') activeDependencyContainer;
  @ViewChild('3dependencyTree') dependencyTreeContainer;
  @ViewChild('3showUpward') showUpwardContainer;
  @ViewChild('3showDownward') showDownwardContainer;
  @ViewChild('3headerBackground') headerBackground;
  @ViewChild('3canvas') canvas: ElementRef;
  @ViewChild('3canvasContainer') canvasContainer;

  public onShowUpwardChanged(): void {
    this.checkUp = !this.checkUp;
    this.draw(() => this.loadDependencies(this.node, this.checkChanged));
  }

  public onShowDownwardChanged(): void {
    this.checkDown = !this.checkDown;
    this.draw(() => this.loadDependencies(this.node, this.checkChanged));
  }

  public screenshotListener(): void {
    const rootList = document.getElementById('3list__root');
    html2canvas(this.canvasContainer.nativeElement, {
      width: rootList.offsetWidth,
      height: rootList.offsetHeight
    }).then(canvas => {
      const dataUrl = canvas.toDataURL(this.formats[this.selected].format, 1.0);
      if (this.selected === 0 || this.selected === 1) {
        // png and jpeg
        const link = document.createElement('a');
        link.setAttribute('href', dataUrl);
        link.setAttribute('download', 'dependencyStructure.' + this.formats[this.selected].value);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      } else if (this.selected === 2) {
        // pdf
        const pdf = new jspdf({
          orientation: 'l',
          unit: 'pt',
          format: [rootList.offsetWidth, rootList.offsetHeight]
        });
        pdf.addImage(dataUrl, 'PNG', 0, 0, rootList.offsetWidth, rootList.offsetHeight);
        pdf.save('dependencyStructure.pdf');
      }
    });
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.draw(() => this.loadDependencies(this.node, this.checkChanged));
  }

  checkOnActiveDependency(tmp): boolean {
    while (tmp.id !== '') {
      if (tmp === this.activeDependency) {
        return true;
      }
      tmp = tmp.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
    }
    return false;
  }

  findLastHTMLElement(node): HTMLElement {
    let element = document.getElementById(node);
    while (element.offsetParent === null) {
      element = element.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
    }
    return element as HTMLElement;
  }

  loadDependencies(node, checkChanged?): void {
    if (node.dependencies.length > 0 && node.children.length === 0) {
      this.listDependencies(node, checkChanged);
    }
    if (node.children.length > 0) {
      for (const child of node.children) {
        this.loadDependencies(child, checkChanged);
      }
    }
  }

  toggle(currentNode): void {
    const nextSibling = currentNode.nextElementSibling;
    if (nextSibling.classList.contains('nested')) {
      nextSibling.classList.remove('nested');
      if (nextSibling.classList.contains('list__root')) {
        nextSibling.classList.add('active__root');
      } else {
        nextSibling.classList.add('active');
      }
    } else if (nextSibling.classList.contains('active')) {
      this.collapseChildren(currentNode);
      nextSibling.classList.add('nested');
      if (nextSibling.classList.contains('list__root')) {
        nextSibling.classList.remove('active__root');
      } else {
        nextSibling.classList.remove('active');
      }
    }
  }

  collapseChildren(currentNode): void {
    if (currentNode.nextElementSibling) {
      const toCollapse = currentNode.nextElementSibling.getElementsByClassName('clickable');
      for (const collapsible of toCollapse) {
        if (collapsible === this.activeDependency) {
          this.activeDependency = undefined;
          this.activeDependencyContainer.nativeElement.textContent = 'No active dependency chosen.';
        }
        if (collapsible.nextElementSibling) {
          collapsible.nextElementSibling.classList.add('nested');
          collapsible.nextElementSibling.classList.remove('active');
        }
      }
    }
  }

  draw(callback): void {
    // set height of canvas to the height of dependencyTree after toggle
    this.ctx.clearRect(0, 0, this.ctx.canvas.width, this.ctx.canvas.height);
    const rootList = document.getElementById('3list__root');
    this.ctx.canvas.height = rootList.offsetHeight + 10;
    this.ctx.canvas.width = rootList.offsetWidth + 30;
    const canvasContainer = document.getElementById('3canvasContainer');
    canvasContainer.style.width = rootList.offsetWidth + 30 + 'px';
    canvasContainer.style.height = rootList.offsetHeight + 10 + 'px';
    this.headerBackground.nativeElement.style.width = rootList.offsetWidth + 30 + 'px';
    callback.call();
  }

  listDependencies(currentNode, checkChanged?): void {
    // draw arrows to my dependencies and call this function for my dependencies
    if (currentNode.dependencies.length > 0) {
      currentNode.dependencies.forEach(dependency => {
        // find last visible element for dependency as end
        let end = this.findLastHTMLElement(dependency.path) as HTMLElement;
        // find last visible element for currentNode as start
        let start = this.findLastHTMLElement(currentNode.path) as HTMLElement;

        // if activeDependency is set, draw only activeDependency related dependencies
        if (this.activeDependency !== undefined) {
          // activeDependency is set and neither start or end
          let toDraw = this.checkOnActiveDependency(start);
          if (!toDraw) {
            toDraw = this.checkOnActiveDependency(end);
          }
          if (!toDraw) {
            return;
          }
        }

        start = start.parentNode as HTMLElement;
        end = end.parentNode as HTMLElement;

        // use jquery for position calculation because plain js position calculation working with offsets returns
        // different values for chrome and firefox
        // (ref: https://stackoverflow.com/questions/1472842/firefox-and-chrome-give-different-values-for-offsettop).
        const startx = $(start).offset().left + start.offsetWidth / 2;
        let starty = $(start).offset().top + start.offsetHeight - $(this.ctx.canvas).offset().top;
        const endx = $(end).offset().left + end.offsetWidth / 2;
        let endy = $(end).offset().top - $(this.ctx.canvas).offset().top;

        // ignore all arrows with same start and end node
        if (start !== end) {
          if (dependency.changed === 'ADD') {
            // check if downward dependencies should be shown
            if (this.checkDown && starty < endy) {
              this.canvasArrow(startx, starty, endx, endy, 'blue', 1, false);
            }
            // check if upward Dependencies should be shown
            if (this.checkUp && starty > endy) {
              starty -= start.offsetHeight;
              endy += end.offsetHeight;
              this.canvasArrow(startx, starty, endx, endy, 'blue', 3, true);
            }
          } else if (dependency.changed === 'DELETE') {
            // check if downward dependencies should be shown
            if (this.checkDown && starty < endy) {
              this.canvasArrow(startx, starty, endx, endy, 'red', 1, false);
            }
            // check if upward Dependencies should be shown
            if (this.checkUp && starty > endy) {
              starty -= start.offsetHeight;
              endy += end.offsetHeight;
              this.canvasArrow(startx, starty, endx, endy, 'red', 3, true);
            }
            // TODO add more color codes for more change types?
          } else if (!checkChanged) {
            // check if downward dependencies should be shown
            if (this.checkDown && starty < endy) {
              this.canvasArrow(startx, starty, endx, endy, 'black', 1, false);
            }
            // check if upward Dependencies should be shown
            if (this.checkUp && starty > endy) {
              starty -= start.offsetHeight;
              endy += end.offsetHeight;
              this.canvasArrow(startx, starty, endx, endy, 'black', 3, true);
            }
          }
        }
      });
    }
  }

  canvasArrow(fromx, fromy, tox, toy, color, width?, dashed?): void {
    if (width === undefined) {
      width = 1;
    }
    if (dashed === undefined) {
      dashed = true;
    }

    this.ctx.lineWidth = width;
    const headlen = 10;

    // draw curved line
    this.ctx.beginPath();
    this.ctx.setLineDash((dashed ? [10] : [0]));
    this.ctx.moveTo(fromx, fromy);
    this.ctx.strokeStyle = color;
    // span right triangle with X, Y and Z with X = (fromx, fromy) and Y = (tox, toy) and Z as the point at the right angle
    // calculate all sides x, y as the sides leading to the right angle
    const x = Math.abs(fromx - tox);
    const y = Math.abs(fromy - toy);
    // calculate z with (zx, zy)
    let angle;
    // tslint:disable-next-line:radix
    if (Math.abs(parseInt(fromx) - parseInt(tox)) < 10) {
      // draw line from X to Y
      this.ctx.lineTo(tox, toy);
      this.ctx.stroke();
      // calculate angle for arrow head in relation to line
      angle = Math.atan2(toy - fromy, tox - fromx);
    } else {
      let zx;
      let zy;

      if (fromx <= tox && fromy <= toy) {
        zx = Math.max(fromx, tox) - x;
        zy = Math.max(fromy, toy);
      } else if (fromx <= tox && fromy > toy) {
        zx = Math.max(fromx, tox);
        zy = Math.min(fromy, toy) + y;
      } else if (fromx > tox && fromy <= toy) {
        zx = Math.min(fromx, tox) + x;
        zy = Math.max(fromy, toy);
      } else if (fromx > tox && fromy > toy) {
        zx = Math.min(fromx, tox);
        zy = Math.min(fromy, toy) + y;
      }

      // draw quadratic curve from X over Z to Y
      this.ctx.quadraticCurveTo(zx, zy, tox, toy);
      this.ctx.stroke();
      // calculate angle for arrow head in relation to line
      angle = Math.atan2(toy - zy, tox - zx);
    }

    // draw arrow head
    this.ctx.beginPath();
    this.ctx.moveTo(tox, toy);
    this.ctx.setLineDash([0]);
    this.ctx.lineTo(tox - headlen * Math.cos(angle - Math.PI / 6), toy - headlen * Math.sin(angle - Math.PI / 6));
    this.ctx.moveTo(tox, toy);
    this.ctx.lineTo(tox - headlen * Math.cos(angle + Math.PI / 6), toy - headlen * Math.sin(angle + Math.PI / 6));
    this.ctx.stroke();
  }
}
