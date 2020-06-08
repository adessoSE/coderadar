import {
  Camera,
  Intersection,
  Object3D,
  PerspectiveCamera,
  Raycaster,
  Scene,
  Vector2, Vector3,
  WebGLRenderer
} from 'three';
import {FocusService} from '../../service/focus.service';
import {TooltipService} from '../../service/tooltip.service';
import {VisualizationConfig} from "../../VisualizationConfig";

export class InteractionHandler {

  enabled = false;

  raycaster: Raycaster = new Raycaster();

  mouse: Vector2 = new Vector2();
  mouseForRaycaster: Vector2 = new Vector2();

  screenOffset: Vector2 = new Vector2();
  screenDimensions: Vector2 = new Vector2();
  localMousePosition: Vector2 = new Vector2();

  hoveredElementUuid = undefined;
  clickedElementUuid = undefined;

  startingPosition: { x?: number, y?: number } = {};

  constructor(
    private scene: Scene,
    private renderer: WebGLRenderer,
    private isMergedView: boolean,
    private focusService: FocusService,
    private tooltipService: TooltipService,
    private tooltipLine: Object3D,
    private highlightBox: Object3D
  ) {
    this.bindEvents();
    this.focusService.elementHighlighted$.subscribe((elementName) => {
      if (elementName === '') {
        this.highlightBox.visible = false;

      } else {
        const addedMargin = VisualizationConfig.HIGHLIGHT_BOX_MARGIN;

        const target: Object3D = this.scene.getObjectByName(elementName);
        let shouldBeHighlighted = true;
        if (!elementName.includes('.')) {shouldBeHighlighted = false; }// should not highlight when the element is not a file
        if (target && shouldBeHighlighted) {
          this.highlightBox.visible = true;
          this.highlightBox.position.copy(new Vector3(target.position.x + target.scale.x / 2,
            target.position.y + target.scale.y / 2, target.position.z + target.scale.z / 2));
          this.highlightBox.scale.copy(target.scale).addScalar(addedMargin);
        } else {
          this.highlightBox.visible = false;
        }
      }
    });
  }

  setIsMergedView(isMergedView: boolean) {
    this.isMergedView = isMergedView;
  }

  update(camera: PerspectiveCamera) {
    if (!this.enabled) {
      return;
    }

    this.raycaster.setFromCamera(this.mouseForRaycaster, camera);
    const intersects: Intersection[] = this.raycaster.intersectObjects(this.scene.children);
    const intersection: Intersection = this.findFirstNonHelperBlockIntersection(intersects);
    const target = intersection ? intersection.object : undefined;

    this.updateTooltip(target, intersection, camera);
  }

  updateTooltip(target: Object3D, intersection: Intersection, camera: Camera) {
    if (target) {
      if (target.uuid !== this.hoveredElementUuid) {
        this.tooltipService.setContent({
          elementName: target.userData.elementName,
          metrics: target.userData.metrics
        });
        this.hoveredElementUuid = target.uuid;
      }


      let tooltipPos: Vector2;
      const vCameraDistance: Vector3 = intersection.point.clone().sub(camera.position);
      const cameraDistance: number = vCameraDistance.length();
      const tooltipHover = cameraDistance * 0.1;
      const tooltipTipSize = cameraDistance * 0.1;
      const tooltipLineArrow = this.tooltipLine.children[0];
      this.tooltipLine.position.copy(new Vector3(0, tooltipHover, 0).add(intersection.point));
      this.focusService.highlightElement(target.userData.elementName);
      tooltipPos = this.worldPositionToScreenPosition(this.tooltipLine.position.clone(), camera);
      // Make the line that hovers the tooltip longer based on camera distance
      this.tooltipLine.scale.setY(tooltipHover);
      // Make the sphere at the cursor change size based on camera distance
      tooltipLineArrow.scale.set(tooltipTipSize, tooltipTipSize / tooltipHover, tooltipTipSize);

      this.setTooltipVisible(true);
      this.tooltipService.setMousePosition({x: tooltipPos.x, y: tooltipPos.y});
    } else {
      this.setTooltipVisible(false);
      this.focusService.highlightElement('');
    }
  }

  onDocumentMouseOver() {
    this.enabled = true;
  }

  onDocumentMouseOut() {
    this.enabled = false;
    this.setTooltipVisible(false);
  }


  onDocumentMouseMove(event) {
    if (!this.enabled) {
      return;
    }

    this.mouse.x = event.clientX;
    this.mouse.y = event.clientY;

    // Canvas object offset
    this.screenOffset.set(this.renderer.domElement.getBoundingClientRect().left, this.renderer.domElement.getBoundingClientRect().top);
    // Canvas object size
    this.screenDimensions.set(this.renderer.domElement.getBoundingClientRect().width,
      this.renderer.domElement.getBoundingClientRect().height);

    // Canvas local mouse position
    this.localMousePosition.set(event.clientX - this.screenOffset.x, event.clientY - this.screenOffset.y);

    this.mouseForRaycaster.x = (this.localMousePosition.x / this.screenDimensions.x) * 2 - 1;
    this.mouseForRaycaster.y = -(this.localMousePosition.y / this.screenDimensions.y) * 2 + 1;
  }

  onDocumentMouseDown(event) {
    this.renderer.domElement.style.cursor = '-webkit-grabbing';

    this.startingPosition = {
      x: event.clientX,
      y: event.clientY
    };
  }

  onDocumentMouseUp(event) {
    this.renderer.domElement.style.cursor = '-webkit-grab';

    if (!this.enabled) {
      return;
    }

    if (Math.abs(event.clientX - this.startingPosition.x) > 0 || Math.abs(event.clientY - this.startingPosition.y) > 0) {
      return;
    }

    const intersects = this.raycaster.intersectObjects(this.scene.children);
    const intersection = this.findFirstNonHelperBlockIntersection(intersects);
    const target = intersection ? intersection.object : undefined;
    if (target) {
      if (event.which === 1) { // left mouse button
        if (target.uuid !== this.clickedElementUuid) {
          this.clickedElementUuid = target.uuid;
        } else {
          this.clickedElementUuid = undefined;
        }

        this.focusService.focusElement(target.name);
      }
    }
  }

  private findFirstNonHelperBlockIntersection(intersections: Intersection[]): Intersection {
    if (intersections.length > 0) {
      for (let i = 0; i < intersections.length; i++) {
        // find the first block that is not a helper block
        // this lets the clicks go through the helper blocks
        if (!intersections[i].object.userData.isHelper) {
          return intersections[i];
        }
      }
    }

    return undefined;
  }

  private worldPositionToScreenPosition(worldPosition: Vector3, camera: Camera): Vector2 {
    const screenCoordinate: Vector3 = worldPosition.project(camera);
    const screenPosition: Vector2 = new Vector2(
      this.screenOffset.x + ( (screenCoordinate.x + 1) * this.screenDimensions.x / 2),
      this.screenOffset.y + (-(screenCoordinate.y - 1) * this.screenDimensions.y / 2));
    return screenPosition;
  }

  private setTooltipVisible(visible: boolean) {

    if (visible) { this.tooltipService.show(); } else { this.tooltipService.hide(); }
    this.tooltipLine.visible = visible;
  }

  private bindEvents() {
    this.renderer.domElement.addEventListener('mouseover', this.onDocumentMouseOver.bind(this), false);
    this.renderer.domElement.addEventListener('mouseout', this.onDocumentMouseOut.bind(this), false);
    this.renderer.domElement.addEventListener('mousemove', this.onDocumentMouseMove.bind(this), false);
    this.renderer.domElement.addEventListener('mousedown', this.onDocumentMouseDown.bind(this), false);
    this.renderer.domElement.addEventListener('mouseup', this.onDocumentMouseUp.bind(this), false);
  }

}
