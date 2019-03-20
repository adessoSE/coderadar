import {Intersection, Object3D, PerspectiveCamera, Raycaster, Scene, Vector2, WebGLRenderer} from 'three';
import {FocusService} from '../../../service/focus.service';
import {TooltipService} from '../../../service/tooltip.service';
import {ScreenType} from '../../../model/enum/ScreenType';

export class InteractionHandler {

    enabled = false;

    raycaster: Raycaster = new Raycaster();
    mouse: Vector2 = new Vector2();
    mouseForRaycaster: Vector2 = new Vector2();

    hoveredElementUuid = undefined;
    clickedElementUuid = undefined;

    startingPosition: {x?: number, y?: number} = {};

    constructor(
        private scene: Scene,
        private renderer: WebGLRenderer,
        private screenType: ScreenType,
        private isMergedView: boolean,
        private focusService: FocusService,
        private tooltipService: TooltipService
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
        const intersects = this.raycaster.intersectObjects(this.scene.children);
        const target = this.findFirstNonHelperBlock(intersects);

        this.updateTooltip(target);
    }

    private findFirstNonHelperBlock(intersections: Intersection[]): Object3D {
        if (intersections.length > 0) {
            for (let i = 0; i < intersections.length; i++) {
                // find the first block that is not a helper block
                // this lets the clicks go through the helper blocks
                if (!intersections[i].object.userData.isHelper) {
                    return intersections[i].object;
                }
            }
        }

        return undefined;
    }

    updateTooltip(target: Object3D) {
        if (target) {
            if (target.uuid !== this.hoveredElementUuid) {
                this.tooltipService.setContent({
                    elementName: target.userData.elementName,
                    metrics: target.userData.metrics
                });
                this.hoveredElementUuid = target.uuid;
            }

            this.tooltipService.show();
            this.tooltipService.setMousePosition({x: this.mouse.x, y: this.mouse.y});
        } else {
            this.tooltipService.hide();
        }
    }

    onDocumentMouseOver() {
        this.enabled = true;
    }

    onDocumentMouseOut() {
        this.enabled = false;
        this.tooltipService.hide();
    }

    onDocumentMouseMove(event) {
        if (!this.enabled) {
            return;
        }

        this.mouse.x = event.clientX;
        this.mouse.y = event.clientY;

        const screenOffset = this.screenType === ScreenType.LEFT ? 0 : this.getScreenWidth();

        this.mouseForRaycaster.x = ((event.clientX - screenOffset) / this.getScreenWidth()) * 2 - 1;
        this.mouseForRaycaster.y = -(event.clientY / window.innerHeight) * 2 + 1;
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
        const target = this.findFirstNonHelperBlock(intersects);
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

    private bindEvents() {
        this.renderer.domElement.addEventListener('mouseover', this.onDocumentMouseOver.bind(this), false);
        this.renderer.domElement.addEventListener('mouseout', this.onDocumentMouseOut.bind(this), false);
        this.renderer.domElement.addEventListener('mousemove', this.onDocumentMouseMove.bind(this), false);
        this.renderer.domElement.addEventListener('mousedown', this.onDocumentMouseDown.bind(this), false);
        this.renderer.domElement.addEventListener('mouseup', this.onDocumentMouseUp.bind(this), false);
    }

    private getScreenWidth() {
        if (this.isMergedView) {
            return window.innerWidth;
        }
        return window.innerWidth / 2;
    }

}
