import {
  Camera,
  Intersection,
  Object3D,
  PerspectiveCamera,
  Raycaster,
  Scene,
  Vector2,
  Vector3,
  WebGLRenderer
} from 'three';
import {FocusService} from '../../service/focus.service';
import {TooltipService} from '../../service/tooltip.service';
import {ScreenType} from "../../enum/ScreenType";
import {ScreenInteractionService} from "../../service/screen-interaction.service";
import {NodeType} from "../../enum/NodeType";

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
    private screenType: ScreenType,
    private isMergedView: boolean,
    private focusService: FocusService,
    private screenInteractionService:ScreenInteractionService,
    private tooltipService: TooltipService,
    private tooltipLine: Object3D
  ) {
    this.bindEvents();
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
        if(!target.userData.elementName){
          console.error(`No userdata on element {0}`,target);
        }
        this.tooltipService.setContent({
          elementName: target.userData.elementName,
          metrics: target.userData.metrics
        },this.screenType);
        if(this.screenInteractionService.getCounterpart(target)){
          this.tooltipService.setContent({
            elementName: this.screenInteractionService.getCounterpart(target).userData.elementName,
            metrics: this.screenInteractionService.getCounterpart(target).userData.metrics
          },this.screenInteractionService.otherType(this.screenType));
        }else{
          if(this.screenType === ScreenType.LEFT){
            this.tooltipService.setContent({
              elementName: "This file was removed",
              metrics: null
            },this.screenInteractionService.otherType(this.screenType));
          }else{
            this.tooltipService.setContent({
              elementName: "Not yet created here",
              metrics: null
            },this.screenInteractionService.otherType(this.screenType));
          }
        }
        this.hoveredElementUuid = target.uuid;
      }
      const vCameraDistance: Vector3 = intersection.point.clone().sub(camera.position);
      const cameraDistance: number = vCameraDistance.length();
      var cursorScale = cameraDistance * 0.1;
      var cursorPosition = intersection.point.clone();
      this.screenInteractionService.setCursorState(cursorPosition,true,cursorScale);

      this.screenInteractionService.setMouseHighlight(target.name);
      this.tooltipService.show(null)
    } else {
      this.tooltipService.hide(null)
      this.screenInteractionService.setCursorState(null,false)
      this.screenInteractionService.setMouseHighlight("");
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
        if(event.shiftKey){
          this.screenInteractionService.toggleSelect(target.name);
        }else{
          this.screenInteractionService.select(target.name);
        }

        if (target.uuid !== this.clickedElementUuid) {
          this.clickedElementUuid = target.uuid;
        } else {
          this.clickedElementUuid = undefined;
        }
      }
    }else{
      this.screenInteractionService.resetSelection();
    }


  }

  onDocumentDoubleClick(event){
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
      for (const i of intersections) {
        // find the first block that is not a helper block
        // this lets the clicks go through the helper blocks
        if (!intersections[i].object.userData.isHelper && intersections[i].object.userData.type!==NodeType.CONNECTION) {
          return intersections[i];
        }
      }
    }

    return undefined;
  }

  private worldPositionToScreenPosition(worldPosition: Vector3, camera: Camera): Vector2 {
    const screenCoordinate: Vector3 = worldPosition.project(camera);
    const screenPosition: Vector2 = new Vector2(
      this.screenOffset.x + (( (screenCoordinate.x + 1) * this.screenDimensions.x / 2)),
      this.screenOffset.y + ((-(screenCoordinate.y - 1) * this.screenDimensions.y / 2)));
    return screenPosition;
  }

  private setTooltipVisible(visible: boolean) {
    this.tooltipLine.visible = visible;
  }

  private bindEvents() {
    this.renderer.domElement.addEventListener('mouseover', this.onDocumentMouseOver.bind(this), false);
    this.renderer.domElement.addEventListener('mouseout', this.onDocumentMouseOut.bind(this), false);
    this.renderer.domElement.addEventListener('mousemove', this.onDocumentMouseMove.bind(this), false);
    this.renderer.domElement.addEventListener('mousedown', this.onDocumentMouseDown.bind(this), false);
    this.renderer.domElement.addEventListener('mouseup', this.onDocumentMouseUp.bind(this), false);
    this.renderer.domElement.addEventListener('dblclick', this.onDocumentDoubleClick.bind(this), false);
  }

}
