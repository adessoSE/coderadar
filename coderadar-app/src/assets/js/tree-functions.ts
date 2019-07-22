export function toggle(currentNode, activeDependency, ctx, headerBackground) {
  if (currentNode.nextSibling.classList.contains('nested')) {
    currentNode.nextSibling.classList.remove('nested');
    if (currentNode.nextSibling.classList.contains('list__root')) {
      currentNode.nextSibling.classList.add('active__root');
    } else {
      currentNode.nextSibling.classList.add('active');
    }
    expand(currentNode as HTMLElement)
  } else if (currentNode.nextSibling.classList.contains('active')) {
    collapseChildren(currentNode, activeDependency);
    currentNode.nextSibling.classList.add('nested');
    if (currentNode.nextSibling.classList.contains('list__root')) {
      currentNode.nextSibling.classList.remove('active__root');
    } else {
      currentNode.nextSibling.classList.remove('active');
    }
    collapse(currentNode as HTMLElement)
  }
  // set height of canvas to the height of dependencyTree after toggle
  ctx.canvas.height = document.getElementById('3list__root').offsetHeight;
  ctx.canvas.width = document.getElementById('3list__root').offsetWidth;
  headerBackground.style.width = document.getElementById('3list__root').offsetWidth + 'px';
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
  let x = Math.abs(fromx - tox), y = Math.abs(fromy - toy);
  // calculate z with (zx, zy)
  let zx, zy;
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
  let angle = Math.atan2(toy - zy, tox - zx);
  // draw arrow head
  context.beginPath();
  context.moveTo(tox, toy);
  context.setLineDash([0]);
  context.lineTo(tox - headlen * Math.cos(angle - Math.PI / 6), toy - headlen * Math.sin(angle - Math.PI / 6));
  context.moveTo(tox, toy);
  context.lineTo(tox - headlen * Math.cos(angle + Math.PI / 6), toy - headlen * Math.sin(angle + Math.PI / 6));
  context.stroke();
}

function collapseChildren(currentNode, activeDependency) {
  if (currentNode.nextSibling) {
    let toCollapse = currentNode.nextSibling.getElementsByClassName('clickable');
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

export function findLastHTMLElement(node) {
  // let element;
  // set element = findById(packageName)
  // while element not visible
  //   element = element.parent
  let element = document.getElementById(node.path);
  while (element.offsetParent === null) {
    element = iterateTree(element)
  }
  return element as HTMLElement;
}

export function iterateTree(tmp) {
  if (tmp.parentNode.firstChild !== tmp) {
    return tmp.parentNode.children[Array.from(tmp.parentNode.children).indexOf(tmp) - 1] as HTMLElement;
  }
  if (tmp.parentNode.parentNode.parentNode.parentNode.parentNode.children.length > 2) {
    tmp = tmp.parentNode.parentNode.parentNode.parentNode.parentNode as HTMLElement;
    return tmp.children[tmp.children.length - 2] as HTMLElement;
  } else {
    return tmp.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
  }
}

export function iterateTreeSkipInline(tmp) {
  if (tmp.parentNode.parentNode.parentNode.parentNode.parentNode.children.length > 2) {
    tmp = tmp.parentNode.parentNode.parentNode.parentNode.parentNode as HTMLElement;
    return tmp.children[tmp.children.length - 2] as HTMLElement;
  } else {
    return tmp.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
  }
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
