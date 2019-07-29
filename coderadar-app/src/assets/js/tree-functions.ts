import html2canvas from 'html2canvas';
import * as $ from 'jquery';

export function addTable(child, callback, htmlBuffer) {
  if (child.children.length > 1) {
    htmlBuffer.push(`<table class="list${child.packageName !== '' ? ' nested' : ''}">`);
    htmlBuffer.push(callback.call());
    htmlBuffer.push('</table>');
  } else {
    htmlBuffer.push(callback.call());
  }
  return htmlBuffer;
}

export function canvasArrow(context, fromx, fromy, tox, toy, color, width?, dashed?) {
  if (width === undefined) {
    width = 1;
  }
  if (dashed === undefined) {
    dashed = true;
  }

  context.lineWidth = width;
  const headlen = 10;

  // draw curved line
  context.beginPath();
  context.setLineDash((dashed ? [10] : [0]));
  context.moveTo(fromx, fromy);
  context.strokeStyle = color;
  // span right triangle with X, Y and Z with X = (fromx, fromy) and Y = (tox, toy) and Z as the point at the right angle
  // calculate all sides x, y as the sides leading to the right angle
  const x = Math.abs(fromx - tox);
  const y = Math.abs(fromy - toy);
  // calculate z with (zx, zy)
  let angle;
  // tslint:disable-next-line:radix
  if (parseInt(fromx) === parseInt(tox)) {
    // draw line from X to Y
    context.lineTo(tox, toy);
    context.stroke();
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
    context.quadraticCurveTo(zx, zy, tox, toy);
    context.stroke();
    // calculate angle for arrow head in relation to line
    angle = Math.atan2(toy - zy, tox - zx);
  }

  // draw arrow head
  context.beginPath();
  context.moveTo(tox, toy);
  context.setLineDash([0]);
  context.lineTo(tox - headlen * Math.cos(angle - Math.PI / 6), toy - headlen * Math.sin(angle - Math.PI / 6));
  context.moveTo(tox, toy);
  context.lineTo(tox - headlen * Math.cos(angle + Math.PI / 6), toy - headlen * Math.sin(angle + Math.PI / 6));
  context.stroke();
}

export function checkHandler(check, ctx, callback) {
  check = !check;
  ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
  callback.call();
  return check;
}

export function checkOnActiveDependency(tmp, activeDependency) {
  while (!tmp.classList.contains('list__root')) {
    if (tmp === activeDependency) {
      return true;
    }
    tmp = iterateTree(tmp);
  }
  return false;
}

export function collapse(element) {
  while ((element.parentNode.parentNode.parentNode.parentNode.parentNode as HTMLElement).id !== '3dependencyTree') {
    (element.parentNode as HTMLElement).style.display = 'inline-block';
    if (element.offsetWidth > (element.nextSibling as HTMLElement).offsetWidth) {
      (element.parentNode as HTMLElement).style.display = 'inline-grid';
    } else {
      (element.parentNode as HTMLElement).style.display = 'inline-block';
    }
    element = iterateTreeSkipInline(element);
  }
}

export function expand(element) {
  while ((element.parentNode.parentNode.parentNode.parentNode.parentNode as HTMLElement).id !== '3dependencyTree') {
    (element.parentNode as HTMLElement).style.display = 'inline-block';
    if (element.offsetWidth < (element.nextSibling as HTMLElement).offsetWidth) {
      (element.parentNode as HTMLElement).style.display = 'inline-block';
    } else {
      (element.parentNode as HTMLElement).style.display = 'inline-grid';
    }
    element = iterateTreeSkipInline(element);
  }
}

export function findLastHTMLElement(node) {
  // let element;
  // set element = findById(packageName)
  // while element not visible
  //   element = element.parent
  let element = document.getElementById(node.path);
  while (element.offsetParent === null) {
    element = iterateTree(element);
  }
  return element as HTMLElement;
}

export function iterateTree(tmp) {
  if (tmp.parentNode.firstChild !== tmp) {
    return tmp.parentNode.children[Array.from(tmp.parentNode.children).indexOf(tmp) - 1] as HTMLElement;
  }
  return iterateTreeSkipInline(tmp);
}

export function iterateTreeSkipInline(tmp) {
  if (tmp.parentNode.parentNode.parentNode.parentNode.parentNode.children.length > 2) {
    tmp = tmp.parentNode.parentNode.parentNode.parentNode.parentNode as HTMLElement;
    return tmp.children[tmp.children.length - 2] as HTMLElement;
  } else {
    return tmp.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
  }
}

export function loadDependencies(node, activeDependency, checkUp, checkDown, ctx, checkChanged?) {
  if (node.dependencies.length > 0 && node.children.length === 0) {
    listDependencies(node, activeDependency, checkUp, checkDown, ctx, checkChanged);
  }
  if (node.children.length > 0) {
    for (const child of node.children) {
      loadDependencies(child, activeDependency, checkUp, checkDown, ctx, checkChanged);
    }
  }
}

export function resizeHandler(ctx, headerBackground, node, callback) {
  window.addEventListener('click', () => {
    draw(ctx, headerBackground, callback);
  });
}

export function screenshotListener(element) {
  document.getElementById(element).addEventListener('click', () => {
    html2canvas(document.getElementById('3canvasContainer'), {
      width: document.getElementById('3list__root').offsetWidth,
      height: document.getElementById('3list__root').offsetHeight
    }).then(canvas => {
      const link = document.createElement('a');
      link.href = canvas.toDataURL('image/jpg');
      link.setAttribute('href', canvas.toDataURL('image/jpg'));
      link.setAttribute('download', 'dependencyStructure.jpg');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    });
  });
}

export function timeoutDraw(ctx, headerBackground, callback) {
  window.setTimeout(() => {
    draw(ctx, headerBackground, callback);
  }, 20);
}

export function toggle(currentNode, activeDependency, ctx, headerBackground) {
  if (currentNode.nextSibling.classList.contains('nested')) {
    currentNode.nextSibling.classList.remove('nested');
    if (currentNode.nextSibling.classList.contains('list__root')) {
      currentNode.nextSibling.classList.add('active__root');
    } else {
      currentNode.nextSibling.classList.add('active');
    }
    expand(currentNode as HTMLElement);
  } else if (currentNode.nextSibling.classList.contains('active')) {
    collapseChildren(currentNode, activeDependency);
    currentNode.nextSibling.classList.add('nested');
    if (currentNode.nextSibling.classList.contains('list__root')) {
      currentNode.nextSibling.classList.remove('active__root');
    } else {
      currentNode.nextSibling.classList.remove('active');
    }
    collapse(currentNode as HTMLElement);
  }
  // set height of canvas to the height of dependencyTree after toggle
  ctx.canvas.height = document.getElementById('3list__root').offsetHeight;
  ctx.canvas.width = document.getElementById('3list__root').offsetWidth;
  headerBackground.style.width = document.getElementById('3list__root').offsetWidth + 'px';
}

function collapseChildren(currentNode, activeDependency) {
  if (currentNode.nextSibling) {
    const toCollapse = currentNode.nextSibling.getElementsByClassName('clickable');
    for (let i = 0; i < toCollapse.length; i++) {
      if (toCollapse[i] === activeDependency) {
        activeDependency = undefined;
        document.getElementById('3activeDependency').textContent = 'No active dependency chosen.';
      }
      if (toCollapse[i].nextSibling) {
        toCollapse[i].nextSibling.classList.add('nested');
        toCollapse[i].nextSibling.classList.remove('active');
      }
    }
  }
}

function draw(ctx, headerBackground, callback) {
  ctx.canvas.height = document.getElementById('3list__root').offsetHeight;
  ctx.canvas.width = document.getElementById('3list__root').offsetWidth;
  headerBackground.style.width = document.getElementById('3list__root').offsetWidth + 'px';
  callback.call();
}

function listDependencies(currentNode, activeDependency, checkUp, checkDown, ctx, checkChanged?) {
  // ctx.lineWidth = 1;
  // draw arrows to my dependencies and call this function for my dependencies
  if (currentNode.dependencies.length > 0) {
    currentNode.dependencies.forEach(dependency => {
      // find last visible element for dependency as end
      let end = findLastHTMLElement(dependency) as HTMLElement;
      // find last visible element for currentNode as start
      let start = findLastHTMLElement(currentNode) as HTMLElement;

      // if activeDependency is set, draw only activeDependency related dependencies
      if (activeDependency !== undefined) {
        // activeDependency is set and neither start or end
        let toDraw = checkOnActiveDependency(start, activeDependency);
        if (!toDraw) {
          toDraw = checkOnActiveDependency(end, activeDependency);
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
      let starty = $(start).offset().top + start.offsetHeight - $(ctx.canvas).offset().top;
      const endx = $(end).offset().left + end.offsetWidth / 2;
      let endy = $(end).offset().top - $(ctx.canvas).offset().top;

      // ignore all arrows with same start and end node
      if (start !== end) {
        if (dependency.changed === 'ADD') {
          // check if downward dependencies should be shown
          if (checkDown && starty < endy) {
            canvasArrow(ctx, startx, starty, endx, endy, 'blue', 1, false);
          }
          // check if upward Dependencies should be shown
          if (checkUp && starty > endy) {
            starty -= start.offsetHeight;
            endy += end.offsetHeight;
            canvasArrow(ctx, startx, starty, endx, endy, 'blue', 3, true);
          }
        } else if (dependency.changed === 'DELETE') {
          // check if downward dependencies should be shown
          if (checkDown && starty < endy) {
            canvasArrow(ctx, startx, starty, endx, endy, 'red', 1, false);
          }
          // check if upward Dependencies should be shown
          if (checkUp && starty > endy) {
            starty -= start.offsetHeight;
            endy += end.offsetHeight;
            canvasArrow(ctx, startx, starty, endx, endy, 'red', 3, true);
          }
        } else if (!checkChanged && (dependency.changed === null || dependency.changed === undefined)) {
          // check if downward dependencies should be shown
          if (checkDown && starty < endy) {
            canvasArrow(ctx, startx, starty, endx, endy, 'black', 1, false);
          }
          // check if upward Dependencies should be shown
          if (checkUp && starty > endy) {
            starty -= start.offsetHeight;
            endy += end.offsetHeight;
            canvasArrow(ctx, startx, starty, endx, endy, 'black', 3, true);
          }
        }
      }
    });
  }
}
