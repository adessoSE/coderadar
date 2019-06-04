import html2canvas from 'html2canvas';

let jsonData;
let ctx;
let htmlBuffer = [];
let checkDown;
let checkUp;
let headerBackground;

export function afterLoad() {
  let data = JSON.parse((document.getElementById('3input') as HTMLInputElement).value);
  jsonData = data;
  buildRoot(data);
  document.getElementById('3dependencyTree').innerHTML = htmlBuffer.join('');
  checkUp = (document.getElementById('3showUpward') as HTMLInputElement).checked;
  checkDown = (document.getElementById('3showDownward') as HTMLInputElement).checked;
  ctx = (document.getElementById('3canvas') as HTMLCanvasElement).getContext('2d');
  headerBackground = (document.getElementById('3headerBackground') as HTMLElement);

  let toggler = document.getElementsByClassName('clickable');
  for (let i = 0; i < toggler.length; i++) {
    toggler[i].addEventListener('click', () => {
      toggle(toggler[i]);
      ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
      loadDependencies(data);
    });
  }
  for (let i = 0; i < toggler.length; i++) {
    let element = toggler[i] as HTMLElement;
    while ((element.parentNode.parentNode.parentNode.parentNode.parentNode as HTMLElement).id !== '3dependencyTree') {
      element.style.display = 'inline';
      if (element.offsetWidth > (element.nextSibling as HTMLElement).offsetWidth) {
        element.style.display = 'inline-grid';
      } else {
        element.style.display = 'inline';
      }
      element = element.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild as HTMLElement;
    }
  }
  document.getElementById('3showUpward').addEventListener('change', () => {
    checkUp = (document.getElementById('3showUpward') as HTMLInputElement).checked;
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    loadDependencies(data);
  });
  document.getElementById('3showDownward').addEventListener('change', () => {
    checkDown = (document.getElementById('3showDownward') as HTMLInputElement).checked;
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    loadDependencies(data);
  });
  document.getElementById('3screenshot').addEventListener('click', () => {
    html2canvas(document.getElementById("3canvasContainer"), {
      width: document.getElementById('3list__root').offsetWidth,
      height: document.getElementById('3list__root').offsetHeight
    }).then(canvas => {
      let link = document.createElement("a");
      link.href = canvas.toDataURL('image/jpg');
      link.setAttribute('href', canvas.toDataURL('image/jpg'));
      link.setAttribute('download', 'dependencyStructure.jpg');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    });
  });

  window.addEventListener('resize', () => {
    ctx.canvas.height = document.getElementById('3list__root').offsetHeight;
    ctx.canvas.width = document.getElementById('3list__root').offsetWidth;
    headerBackground.style.width = document.getElementById('3list__root').offsetWidth + 'px';
    loadDependencies(data);
  });

  ctx.canvas.height = document.getElementById('3list__root').offsetHeight;
  ctx.canvas.width = document.getElementById('3list__root').offsetWidth;
  headerBackground.style.width = document.getElementById('3list__root').offsetWidth + 'px';
  loadDependencies(data);
}

function loadDependencies(node) {
  if (node.dependencies.length > 0 && node.children.length === 0) {
    listDependencies(node, ctx);
  }
  if (node.children.length > 0) {
    for (let child of node.children) {
      loadDependencies(child);
    }
  }
}

function buildRoot(currentNode) {
  htmlBuffer.push(`<table id="3list__root" class="list list__root active">`);
  htmlBuffer.push(`<tr><td class="package package__base">` +
    `<span id="${currentNode.packageName}" class="filename-span${currentNode.children.length > 0 && currentNode.packageName !== '' ? ' clickable' : ''}">${currentNode.filename}</span>`);

  if (currentNode.children.length > 0) {
    htmlBuffer.push(`<table class="list${currentNode.packageName !== '' ? ' nested' : ''}">`);
    htmlBuffer.push(buildTree(currentNode));
    htmlBuffer.push('</table>');
  }
  htmlBuffer.push(`</td></tr></table>`);
}

function buildTree(currentNode) {
  let layer = -1;
  htmlBuffer.push('<tr style="display: none">');
  for (const child of currentNode.children) {
    let classString;
    if (child.children.length > 0) {
      classString = 'package';
    } else if (child.dependencies.length > 0) {
      classString = 'class--dependency';
    } else if (child.children.length === 0 && child.dependencies.length === 0) {
      classString = 'class';
    }

    // add line break to long filenames before an uppercase letter except the first letter
    let fileName = '';
    for (let i = 0; i < child.filename.length; i++) {
      let c = child.filename.charAt(i);
      if (i !== 0 && c.toUpperCase() === c) {
        fileName += '<wbr>';
      }
      fileName += c;
    }
    if (layer === child.layer) {
      // add in same row as child
      htmlBuffer.push(`<td class="${classString}">` +
        `<span id="${child.packageName}" class="filename-span${child.children.length > 0 && child.packageName !== '' ? ' clickable' : ''}">${fileName}</span>`);
    } else {
      // create new row
      htmlBuffer.push(`</tr><tr><td class="${classString}">` +
        `<span id="${child.packageName}" class="filename-span${child.children.length > 0 && child.packageName !== '' ? ' clickable' : ''}">${fileName}</span>`);
    }
    layer = child.layer;
    if (child.children.length > 0) {
      htmlBuffer.push(`<table class="list${child.packageName !== '' ? ' nested' : ''}">`);
      htmlBuffer.push(buildTree(child));
      htmlBuffer.push('</table>');
    }
    htmlBuffer.push('</td>')
  }
  htmlBuffer.push(`</tr>`);
}

function toggle(currentNode) {
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
    collapseChildren(currentNode);
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

function listDependencies(currentNode, ctx) {
  ctx.lineWidth = 1;
  // draw arrows to my dependencies and call this function for my dependencies
  if (currentNode.dependencies.length > 0) {
    currentNode.dependencies.forEach(dependency => {
      // find last visible element for dependency as end
      const end = findLastHTMLElement(dependency).parentNode as HTMLElement;
      // find last visible element for currentNode as start
      const start = findLastHTMLElement(currentNode).parentNode as HTMLElement;

      let startx = 0, starty = 0, endx = 0, endy = 0;
      // calculate offsets across all parents for start and end
      let tmp = start;
      do {
        startx += tmp.offsetLeft;
        starty += tmp.offsetTop;
        tmp = tmp.offsetParent as HTMLElement;
      } while (!tmp.classList.contains('list__root'));
      tmp = end;
      do {
        endx += tmp.offsetLeft;
        endy += tmp.offsetTop;
        tmp = tmp.offsetParent as HTMLElement;
      } while (!tmp.classList.contains('list__root'));

      //ignore all arrows with same start and end node
      if (start != end) {
        // check if downward dependencies should be shown
        if (checkDown && starty < endy) {
          canvasArrow(ctx, startx + 13, starty + 13, endx + end.offsetWidth, endy + end.offsetHeight / 2, "black");
        }
        // check if upward Dependencies should be shown
        if (checkUp && starty > endy) {
          canvasArrow(ctx, startx + 13, starty + 13, endx + end.offsetWidth, endy + end.offsetHeight / 2, "red");
        }
      }
    });
  }
}

function findLastHTMLElement(node) {
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

function canvasArrow(context, fromx, fromy, tox, toy, color) {
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

function collapseChildren(currentNode) {
  if (currentNode.nextSibling) {
    let toCollapse = currentNode.nextSibling.getElementsByClassName('clickable');
    for (let i = 0; i < toCollapse.length; i++) {
      if (toCollapse[i].nextSibling) {
        toCollapse[i].nextSibling.classList.add('nested');
        toCollapse[i].nextSibling.classList.remove('active');
      }
    }
  }
}
