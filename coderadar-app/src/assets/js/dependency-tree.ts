let activeDependency = undefined;
let jsonData, rootOffset = 0;
let ctx;

export function afterLoad() {
  let data = JSON.parse((document.getElementById('input') as HTMLInputElement).value);
  jsonData = data;
  document.getElementById('dependencyTree').innerHTML = buildRoot('', data);
  ctx = (document.getElementById('canvas') as HTMLCanvasElement).getContext('2d');
  rootOffset = document.getElementById('header').offsetHeight + 19;

  let toggler = document.getElementsByClassName('clickable');
  for (let i = 0; i < toggler.length; i++) {
    if (toggler[i].parentElement.classList.contains('package')) {
      toggler[i].addEventListener('click', () => {
        toggle(toggler[i]);
        ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
        if (activeDependency) {
          listDependencies(findDataNode(activeDependency), ctx, 0);
        }
      });
    } else if (toggler[i].parentElement.classList.contains('class--dependency')) {
      toggler[i].addEventListener('click', () => {
        toggleDependency(toggler[i]);
      });
    }
  }
}

function buildRoot(htmlString, currentNode) {
  htmlString += `<div class="package package__base">`;
  htmlString += `<span id="${currentNode.packageName}" class="${(currentNode.children.length > 0
    || currentNode.dependencies.length > 0) ? 'clickable' : ''}">${currentNode.filename}</span>`;

  if (currentNode.children.length > 0) {
    htmlString += `<div class="list list__root nested">`;
    currentNode.children.forEach((child) => htmlString = buildTree(htmlString, child));
    htmlString += `</div>`;
  }
  htmlString += `</div>`;
  return htmlString;
}

function buildTree(htmlString, currentNode) {
  let classString;
  if (currentNode.children.length > 0) {
    classString = 'package';
  } else if (currentNode.dependencies.length > 0) {
    classString = 'class--dependency';
  } else if (currentNode.children.length === 0 && currentNode.dependencies.length === 0) {
    classString = 'class';
  }

  htmlString += `<div class="${classString}">`;
  htmlString += `<span id="${currentNode.packageName}" class="${(currentNode.children.length > 0
    || currentNode.dependencies.length > 0) ? 'clickable' : ''}">${currentNode.filename}</span>`;

  if (currentNode.children.length > 0) {
    htmlString += `<div class="list nested">`;
    currentNode.children.forEach((child) => htmlString = buildTree(htmlString, child));
    htmlString += `</div>`;
  }
  htmlString += `</div>`;
  return htmlString;
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

  ctx.canvas.height = document.getElementById('dependencyTree').offsetHeight + 48;
  ctx.canvas.width = document.getElementById('dependencyTree').offsetWidth;
  // set height of canvas
}

function toggleDependency(currentNode) {
  let unset = currentNode.classList.contains('activeDependency');
  if (activeDependency) {
    document.getElementById('activeDependency').innerText = 'Choose File...';
    [].forEach.call(document.getElementsByClassName('activeDependency'), element => {
      element.classList.remove('activeDependency');
    });
    activeDependency = undefined;
    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
  }
  if (!unset) {
    currentNode.classList.add('activeDependency');
    activeDependency = currentNode;
    let dataNode = findDataNode(currentNode);
    listDependencies(dataNode, ctx, 0)
  }
}

function listDependencies(currentNode, ctx, redValue) {
  ctx.lineWidth = 3;
  // draw arrows to my dependencies and call this function for my dependencies
  if (currentNode.dependencies.length > 0) {
    currentNode.dependencies.forEach(dependency => {
      // find start for arrow
      let start;
      // find end for arrow
      const end = findLastHTMLElement(dependency).parentElement;
      ctx.strokeStyle = `rgb(${redValue}, 0, 0)`;
      // draw arrow to dependency
      // draw from start to end
      if (document.getElementById(currentNode.packageName) === activeDependency) {
        start = document.getElementById('activeDependency');
        canvasArrow(ctx, start.offsetLeft + start.offsetWidth, start.offsetTop - rootOffset + start.offsetHeight,
          end.offsetLeft + end.offsetWidth, end.offsetTop - rootOffset + end.offsetHeight / 2);
      } else {
        start = findLastHTMLElement(currentNode).parentElement;
        canvasArrow(ctx, start.offsetLeft, start.offsetTop - rootOffset + start.offsetHeight / 2,
          end.offsetLeft + end.offsetWidth, end.offsetTop - rootOffset + end.offsetHeight / 2);
      }
      // call this function for dependency
      if (dependency.dependencies.length > 0) {
        listDependencies(dependency, ctx, redValue + 40);
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
  let filenameBits = activeDependency.id.split('.');
  document.getElementById('activeDependency')
    .innerText = filenameBits[filenameBits.length - 2] + '.' + filenameBits[filenameBits.length - 1];

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
      if (activeDependency === toCollapse[i]) {
        activeDependency = undefined;
        activeDependency
      }
      if (toCollapse[i].nextSibling) {
        toCollapse[i].nextSibling.classList.add('nested');
        if (toCollapse[i].nextSibling.classList.contains('list__root')) {
          toCollapse[i].nextSibling.classList.remove('active__root');
        } else {
          toCollapse[i].nextSibling.classList.remove('active');
        }
      }
    }
  }
}
