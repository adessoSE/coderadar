let jsonData, rootOffset = 0;
let ctx;
let htmlBuffer = [];

export function afterLoad() {
  let data = JSON.parse((document.getElementById('input') as HTMLInputElement).value);
  jsonData = data;
  buildRoot(data);
  document.getElementById('dependencyTree').innerHTML = htmlBuffer.join('');
  ctx = (document.getElementById('canvas') as HTMLCanvasElement).getContext('2d');
  // rootOffset = 19;
  console.log(rootOffset);
  let toggler = document.getElementsByClassName('clickable');
  for (let i = 0; i < toggler.length; i++) {
    toggler[i].addEventListener('click', () => {
      toggle(toggler[i]);
      ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
      loadDependencies(data);
    });
  }
  ctx.canvas.height = document.getElementById('dependencyTree').offsetHeight;
  ctx.canvas.width = document.getElementById('dependencyTree').offsetWidth;
  loadDependencies(data);
}

function loadDependencies(node) {
  if (node.dependencies.length > 0 && node.children.length === 0) {
    for (let dependency of node.dependencies) {
      listDependencies(dependency, ctx);
    }
  }
  if (node.children.length > 0) {
    for (let child of node.children) {
      loadDependencies(child);
    }
  }
}

function buildRoot(currentNode) {
  htmlBuffer.push(`<table class="table__base">`);
  htmlBuffer.push(`<tr><td id="${currentNode.packageName}" class="package package__base">` +
    `<span class="${currentNode.children.length > 0 ? 'clickable' : ''}">${currentNode.filename}</span>`);

  if (currentNode.children.length > 0) {
    htmlBuffer.push(`<table class="list nested">`);
    htmlBuffer.push(buildTree(currentNode));
    htmlBuffer.push('</table>');
  }
  htmlBuffer.push(`</td></tr></table>`);
}

function buildTree(currentNode) {
  let layer = 0;
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
    if (layer === child.layer) {
      // add in same row as child
      htmlBuffer.push(`<td class="${classString}">` +
        `<span id="${child.packageName}" class="${child.children.length > 0 ? 'clickable' : ''}">${child.filename}</span>`);
    } else {
      // create new row
      htmlBuffer.push(`</tr><tr><td class="${classString}">` +
        `<span id="${child.packageName}" class="${child.children.length > 0 ? 'clickable' : ''}">${child.filename}</span>`);
    }
    layer = child.layer;
    if (child.children.length > 0) {
      htmlBuffer.push(`<table class="list nested">`);
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
  } else if (currentNode.nextSibling.classList.contains('active')) {
    collapseChildren(currentNode);
    currentNode.nextSibling.classList.add('nested');
    if (currentNode.nextSibling.classList.contains('list__root')) {
      currentNode.nextSibling.classList.remove('active__root');
    } else {
      currentNode.nextSibling.classList.remove('active');
    }
  }

  ctx.canvas.height = document.getElementById('dependencyTree').offsetHeight;
  ctx.canvas.width = document.getElementById('dependencyTree').offsetWidth;
  // set height of canvas
}

function listDependencies(currentNode, ctx) {
  ctx.lineWidth = 1;
  // draw arrows to my dependencies and call this function for my dependencies
  if (currentNode.dependencies.length > 0) {
    currentNode.dependencies.forEach(dependency => {
      console.log(`draw from ${currentNode.packageName} to ${dependency.packageName}`);
      // find start for arrow
      // find end for arrow
      const end = findLastHTMLElement(dependency);
      // draw arrow to dependency
      // draw from start to end

      let start = findLastHTMLElement(currentNode);
      // console.log(start);
      // console.log(end);
      canvasArrow(ctx, start.offsetLeft, start.offsetTop - rootOffset + start.offsetHeight / 2,
        end.offsetLeft + end.offsetWidth, end.offsetTop - rootOffset + end.offsetHeight / 2);
      // call this function for dependency
      if (dependency.dependencies.length > 0) {
        listDependencies(dependency, ctx);
      }
    });
  }
}

function findLastHTMLElement(node) {
  let packageName = node.packageName;
  let element;
  while (element === undefined) {
    if (!document.getElementById(packageName) || document.getElementById(packageName).offsetParent === null) {
      packageName = packageName.substring(0, packageName.lastIndexOf('.'));
    } else {
      element = document.getElementById(packageName);
    }
  }
  return element;
}

function findDataNode(clickable) {
  let filenameBits = clickable.id.split('.');

  let dataNode = jsonData;
  let beginIndex = filenameBits.findIndex((filenameBit) => {
    return filenameBit === jsonData.filename;
  });
  let last = filenameBits.splice( -1, 1);
  filenameBits[filenameBits.length - 1] += '.' + last;
  for (let j = beginIndex; j < filenameBits.length; j++) {
    dataNode.children.forEach(child => {
      if (child.filename === filenameBits[j]) {
        dataNode = child;
      }
    });
  }
  return dataNode;
}

function canvasArrow(context, fromx, fromy, tox, toy) {
  // console.log(`draw arrow from (${fromx}|${fromy}) to (${tox}|${toy})`);
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
