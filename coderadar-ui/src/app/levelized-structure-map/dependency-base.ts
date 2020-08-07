import {ElementRef, HostListener, ViewChild} from '@angular/core';
import html2canvas from 'html2canvas';
import * as $ from 'jquery';
import * as jspdf from 'jspdf';
import {AppComponent} from '../app.component';
import {Project} from '../model/project';
import {FORBIDDEN, NOT_FOUND} from 'http-status-codes';
import {EdgeModel} from './edge.model';

export abstract class DependencyBase {

  appComponent = AppComponent;
  projectService: any;
  userService: any;
  router: any;
  project: Project = new Project();
  node: any;
  projectId: number;
  commitName: any;
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
  svg: any;
  readonly headLength = 10;
  drawn: EdgeModel[] = [];
  @ViewChild('3activeDependency') activeDependencyContainer;
  @ViewChild('3canvasContainer') canvasContainer;
  @ViewChild('3zoom') zoomElement;

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
    return tmp.id.indexOf(this.activeDependency.id) >= 0;
  }

  findLastHTMLElement(node): HTMLElement {
    let element = document.getElementById(node);
    while (element.offsetParent === null) {
      element = element.parentNode.parentNode.parentNode.parentNode.parentNode.firstElementChild as HTMLElement;
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
    // clear svg
    $('#3svg').empty();
    this.drawn = [];
    const rootList = document.getElementById('3list__root');
    this.svg = document.getElementById('3svg');
    const zoom = document.getElementById('3zoom');
    const scroll = document.querySelector('[id="3scroll"] > div') as HTMLElement;
    const drawHeight = rootList.offsetHeight;
    const drawWidth = rootList.offsetWidth + 20;
    const containerHeight = window.innerHeight - document.getElementById('3canvasContainer').offsetTop;
    const containerWidth = window.innerWidth;
    (document.querySelector('drag-scroll[id="3scroll"] > div > div') as HTMLElement).style.overflow = 'hidden';

    // set dimensions of zoom and scroll element
    zoom.style.height = containerHeight + 'px';
    zoom.style.width = containerWidth + 'px';
    scroll.style.height = (containerHeight < drawHeight ? containerHeight : drawHeight) + 20 + 'px';
    scroll.style.width = (containerWidth < drawWidth ? containerWidth : drawWidth) + 'px';

    // set styling and dimensions of svg element
    this.svg.style.height = drawHeight + 'px';
    this.svg.style.width = drawWidth + 'px';
    this.svg.style.top = -rootList.offsetHeight + 'px';
    this.svg.style.marginBottom = -rootList.offsetHeight + 10 + 'px';
    if (drawWidth > containerWidth && drawHeight > containerHeight) {
      rootList.style.cursor = 'move';
      zoom.style.cursor = 'move';
    } else if (drawWidth > containerWidth && drawHeight <= containerHeight) {
      rootList.style.cursor = 'ew-resize';
      zoom.style.cursor = 'ew-resize';
    } else if (drawWidth <= containerWidth && drawHeight > containerHeight) {
      rootList.style.cursor = 'ns-resize';
      zoom.style.cursor = 'ns-resize';
    }
    callback.call();
  }

  listDependencies(currentNode, checkChanged?): void {
    // calculate positions for arrows of currentNode and its dependencies
    if (currentNode.dependencies.length > 0) {
      // find last visible element for currentNode as start
      const start = this.findLastHTMLElement(currentNode.path) as HTMLElement;
      let toDraw;
      if (this.activeDependency !== undefined) {
        // activeDependency is set is not start
        toDraw = this.checkOnActiveDependency(start);
      }
      // start = start.parentNode as HTMLElement;
      // use jquery for position calculation because plain js position calculation working with offsets returns
      // different values for chrome and firefox
      // (ref: https://stackoverflow.com/questions/1472842/firefox-and-chrome-give-different-values-for-offsettop).
      const startx = ($(start).offset().left - $(this.svg).offset().left) / this.zoomElement.scale + start.offsetWidth / 2;
      const starty = ($(start).offset().top - $(this.svg).offset().top) /
        this.zoomElement.scale + start.offsetHeight + ($(start).css('padding-top') !== '0px' ? 0 : 5);

      const startTop = starty - start.offsetHeight - ($(start).css('padding-top') !== '0px' ? 0 : 10);
      console.log(parseFloat($(start).css('padding-top')));

      currentNode.dependencies.forEach(dependency => {
        // find last visible element for dependency as end
        const end = this.findLastHTMLElement(dependency.path);

        // if activeDependency is set, draw only activeDependency related dependencies
        if (this.activeDependency !== undefined) {
          // activeDependency is set and is not end
          if (!toDraw) {
            toDraw = this.checkOnActiveDependency(end);
          }
          if (!toDraw) {
            return;
          }
        }

        // end = end.parentNode as HTMLElement;
        const endx = ($(end).offset().left - $(this.svg).offset().left) / this.zoomElement.scale + end.offsetWidth / 2;
        const endy = ($(end).offset().top - $(this.svg).offset().top) /
          this.zoomElement.scale - ($(end).css('padding-bottom') !== '0px' ? 0 : 7);

        const endBottom = endy + end.offsetHeight + ($(end).css('padding-bottom') !== '0px' ? 0 : 14);

        if (start !== end) {
          if (dependency.changed === 'ADD') {
            // check if downward dependencies should be shown
            if (this.checkDown && starty < endy) {
              this.svgArrow(startx, starty, endx, endy, 'blue', 1, false);
            }
            // check if upward Dependencies should be shown
            if (this.checkUp && starty > endy) {
              this.svgArrow(startx, startTop, endx, endBottom, 'blue', 3, true);
            }
          } else if (dependency.changed === 'DELETE') {
            // check if downward dependencies should be shown
            if (this.checkDown && starty < endy) {
              this.svgArrow(startx, starty, endx, endy, 'red', 1, false);
            }
            // check if upward Dependencies should be shown
            if (this.checkUp && starty > endy) {
              this.svgArrow(startx, startTop, endx, endBottom, 'red', 3, true);
            }
            // TODO add more color codes for more change types?
          } else if (!checkChanged) {
            // check if downward dependencies should be shown
            if (this.checkDown && starty < endy) {
              this.svgArrow(startx, starty, endx, endy, 'black', 1, false);
            }
            // check if upward Dependencies should be shown
            if (this.checkUp && starty > endy) {
              this.svgArrow(startx, startTop, endx, endBottom, 'black', 3, true);
            }
          }
        }
      });
    }
  }

  svgArrow(startx, starty, endx, endy, color, width?, dashed?) {
    // reduce number of nodes
    const edge = new EdgeModel(startx, starty, endx, endy, color, width, dashed);
    if (this.drawn.filter(existingEdge => existingEdge.equals(edge)).length > 0) {
      return;
    }

    // angle of arrowhead in relation to the line
    let angle;
    // definition of teh figure to draw, either a line or a curve
    let figureDefinition;

    if (Math.abs(parseInt(startx, 10) - parseInt(endx, 10)) < 10) {
      // draw straight line because there is not enough space for a curve
      figureDefinition = `M ${startx} ${starty} L ${endx} ${endy}`;
      angle = Math.atan2(endy - starty, endx - startx);
    } else {
      // draw a quadratic curve over point Z which builds a right triangle with start and end
      const x = Math.abs(startx - endx);
      const y = Math.abs(starty - endy);
      let zx;
      let zy;

      // calculate position of Z
      if (startx <= endx && starty <= endy) {
        zx = Math.max(startx, endx) - x;
        zy = Math.max(starty, endy);
      } else if (startx <= endx && starty > endy) {
        zx = Math.max(startx, endx);
        zy = Math.min(starty, endy) + y;
      } else if (startx > endx && starty <= endy) {
        zx = Math.min(startx, endx) + x;
        zy = Math.max(starty, endy);
      } else if (startx > endx && starty > endy) {
        zx = Math.min(startx, endx);
        zy = Math.min(starty, endy) + y;
      }
      figureDefinition = `M ${startx} ${starty} Q ${zx} ${zy} ${endx} ${endy}`;
      angle = Math.atan2(endy - zy, endx - zx);
    }
    this.drawFigure(figureDefinition, width, color, dashed);
    this.drawArrowHead(endx, endy, angle, width, color);
    this.drawn.push(edge);
  }

  drawArrowHead(endx, endy, angle, width, color) {
    this.drawFigure('M ' + (endx - this.headLength * Math.cos(angle - Math.PI / 6)) + ' '
      + (endy - this.headLength * Math.sin(angle - Math.PI / 6)) + ' ' + 'L ' + endx + ' ' + endy + ' ' +
      (endx - this.headLength * Math.cos(angle + Math.PI / 6)) + ' ' + (endy - this.headLength * Math.sin(angle + Math.PI / 6)),
      width, color
    );
  }

  drawFigure(figureDefinition: string, width: number, color: string, dashed?: boolean) {
    const figure = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    figure.setAttribute('d', figureDefinition);
    figure.style.stroke = color;
    figure.style.fill = 'transparent';
    figure.style.strokeWidth = `${width}px`;
    if (dashed) {
      figure.style.strokeDasharray = '10';
    }
    this.svg.appendChild(figure);
  }

  getProject(): void {
    this.projectService.getProject(this.projectId)
      .then(response => {
        this.project = new Project(response.body);
      })
      .catch(error => {
        if (error.status && error.status === FORBIDDEN) {
          this.userService.refresh(() => this.getProject());
        } else if (error.status && error.status === NOT_FOUND) {
          this.router.navigate(['/dashboard']);
        }
      });
  }
}
