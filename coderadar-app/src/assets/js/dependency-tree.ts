import * as $ from 'jquery';
import {addTable, checkHandler, expand, loadDependencies, resizeHandler, screenshotListener, timeoutDraw, toggle} from './tree-functions';

let ctx;
let htmlBuffer = [];
let checkDown;
let checkUp;
let headerBackground;
let activeDependency;

$.fn.single_double_click = function(singleClickCallback, doubleClickCallback, timeout?) {
  return this.each(() => {
    let clicks = 0;
    const self = this;
    // if a click occurs
    $(this).click(event => {
      // raise click counter
      clicks++;
      // if this is the first click, start a timer with @timeout millis
      if (clicks === 1) {
        setTimeout(() => {
          // on timer's timeout check if a second click has occurred.
          if (clicks === 1) {
            singleClickCallback.call(self, event);
          } else {
            doubleClickCallback.call(self, event);
          }
          // reset click counter
          clicks = 0;
        }, timeout || 300);
      }
    });
  });
};

export function afterLoad(node) {
  htmlBuffer = [];
  buildRoot(node);
  document.getElementById('3dependencyTree').innerHTML = htmlBuffer.join('');
  checkUp = (document.getElementById('3showUpward') as HTMLInputElement).getAttribute('checked') === 'checked';
  checkDown = (document.getElementById('3showDownward') as HTMLInputElement).getAttribute('checked') === 'checked';
  ctx = (document.getElementById('3canvas') as HTMLCanvasElement).getContext('2d');
  headerBackground = (document.getElementById('3headerBackground') as HTMLElement);

  // add toggle function (click and dblclick)
  const togglers = Array.from(document.getElementsByClassName('clickable'));
  for (const toggler of togglers) {
    $(toggler).single_double_click(() => {
      // set toggler to active dependency
      if (activeDependency === toggler) {
        activeDependency = undefined;
        document.getElementById('3activeDependency').textContent = 'No active dependency chosen.';
      } else {
        activeDependency = toggler;
        // @ts-ignore
        document.getElementById('3activeDependency').textContent = toggler.textContent;
      }
      // clear and draw arrows for active dependency
      ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
      loadDependencies(node, activeDependency, checkUp, checkDown, ctx);
    }, () => {
      if (toggler.nextSibling != null) {
        toggle(toggler, activeDependency, ctx, headerBackground);
        ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
        loadDependencies(node, activeDependency, checkUp, checkDown, ctx);
      }
    });
    // collapse and extend elements
    if (toggler.nextSibling != null) {
      expand(toggler as HTMLElement);
    }
  }

  // show upward listener
  document.getElementById('3showUpward').addEventListener('click', () => {
    checkUp = checkHandler(checkUp, ctx, () =>
      loadDependencies(node, activeDependency, checkUp, checkDown, ctx));
  });
  // show upward listener
  document.getElementById('3showDownward').addEventListener('click', () => {
    checkDown = checkHandler(checkDown, ctx, () =>
      loadDependencies(node, activeDependency, checkUp, checkDown, ctx));
  });
  // screenshot listener
  screenshotListener('3screenshot');
  // resize listener
  resizeHandler(ctx, headerBackground, node, () =>
    loadDependencies(node, activeDependency, checkUp, checkDown, ctx));
  // set canvas format and draw dependencies
  timeoutDraw(ctx, headerBackground, () =>
    loadDependencies(node, activeDependency, checkUp, checkDown, ctx));
}

function buildRoot(currentNode) {
  htmlBuffer.push(`<table id="3list__root" class="list list__root active">`);
  htmlBuffer.push(`<tr><td class="package package__base"><span id="${currentNode.path}" ` +
    `class="filename-span${currentNode.children.length > 0 && currentNode.packageName !== '' ? ' clickable' : ''}">` +
    `${currentNode.filename}</span>`);
  htmlBuffer = addTable(currentNode, () => buildTree(currentNode, currentNode.children.length === 1), htmlBuffer);
  htmlBuffer.push(`</td></tr></table>`);
}

function buildTree(currentNode, span) {
  let level = -1;
  // if currentNode has more than one children, open a hidden tr for displaying child's level
  htmlBuffer.push(currentNode.children.length <= 1 ? '' : '<tr style="display: none">');
  for (const child of currentNode.children) {
    // decide whether child is a package, a class or a class with dependencies
    let classString;
    if (child.children.length > 0) {
      classString = 'package';
    } else if (child.dependencies.length > 0) {
      classString = 'class--dependency';
    } else if (child.children.length === 0 && child.dependencies.length === 0) {
      classString = 'class';
    }

    if (span) {
      htmlBuffer.push(`<span id="${child.path}" class="filename-span` +
        `${(child.children.length > 1 || child.dependencies.length > 0) && child.packageName !== '' ? ' clickable' : ''}">` +
        `${'/' + child.filename}</span>`);

      htmlBuffer = addTable(child, () => buildTree(child, child.children.length === 1), htmlBuffer);
    } else {
      htmlBuffer.push(level !== child.level ? '</tr><tr>' : '');
      htmlBuffer.push(`<td class="${classString}"><span id="${child.path}" class="filename-span` +
        `${(child.children.length > 1 || child.dependencies.length > 0) && child.packageName !== '' ? ' clickable' : ''}">` +
        `${child.filename}</span>`
      );
      level = child.level;
      htmlBuffer = addTable(child, () => buildTree(child, child.children.length === 1), htmlBuffer);
      htmlBuffer.push('</td>');
    }
  }
  htmlBuffer.push(currentNode.children.length <= 1 ? '' : '</tr>');
}
