export function toggle(currentNode, activeDependency, ctx, headerBackground) {
  if (currentNode.nextSibling.classList.contains('nested')) {
    currentNode.nextSibling.classList.remove('nested');
    if (currentNode.nextSibling.classList.contains('list__root')) {
      currentNode.nextSibling.classList.add('active__root');
    } else {
      currentNode.nextSibling.classList.add('active');
    }
    let element = currentNode as HTMLElement;
    while ((element.parentNode.parentNode.parentNode.parentNode.parentNode as HTMLElement).id !== '3dependencyTree') {
      element.style.display = 'inline';
      if (element.offsetWidth > (element.nextSibling as HTMLElement).offsetWidth) {
        element.style.display = 'inline-grid';
      } else {
        element.style.display = 'inline';
      }
      element = element.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
    }
  } else if (currentNode.nextSibling.classList.contains('active')) {
    collapseChildren(currentNode, activeDependency);
    currentNode.nextSibling.classList.add('nested');
    if (currentNode.nextSibling.classList.contains('list__root')) {
      currentNode.nextSibling.classList.remove('active__root');
    } else {
      currentNode.nextSibling.classList.remove('active');
    }
    let element = currentNode as HTMLElement;
    while ((element.parentNode.parentNode.parentNode.parentNode.parentNode as HTMLElement).id !== '3dependencyTree') {
      element.style.display = 'inline';
      if (element.offsetWidth < (element.nextSibling as HTMLElement).offsetWidth) {
        element.style.display = 'inline';
      } else {
        element.style.display = 'inline-grid';
      }
      element = element.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
    }
  }
  // set height of canvas to the height of dependencyTree after toggle
  ctx.canvas.height = document.getElementById('3list__root').offsetHeight;
  ctx.canvas.width = document.getElementById('3list__root').offsetWidth;
  headerBackground.style.width = document.getElementById('3list__root').offsetWidth + 'px';
}

export function canvasArrow(context, fromx, fromy, tox, toy, color) {
  const headlen = 10;

  // draw curved line
  context.beginPath();
  context.setLineDash([10]);
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
  let packageName = node.packageName;
  // let element;
  // set element = findById(packageName)
  // while element not visible
  //   element = element.parent
  let element = document.getElementById(packageName);
  while (element.offsetParent === null) {
    element = element.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
  }
  return element as HTMLElement;
}
