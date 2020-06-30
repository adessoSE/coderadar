import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import * as THREE from 'three';
import {Line, Mesh, Object3D, Scene, Vector3, WebGLRenderer} from 'three';
import * as TWEEN from '@tweenjs/tween.js';

import {Subscription} from 'rxjs';
import {OrbitControls} from 'three-orbitcontrols-ts';
import {AbstractView} from '../view/abstract-view';
import {SplitView} from '../view/split-view';
import {MergedView} from '../view/merged-view';
import {IFilter} from '../../interfaces/IFilter';
import {InteractionHandler} from '../interaction-handler/interaction-handler';
import {VisualizationConfig} from '../../VisualizationConfig';
import {INode} from '../../interfaces/INode';
import {FocusService} from '../../service/focus.service';
import {TooltipService} from '../../service/tooltip.service';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {ComparisonPanelService} from '../../service/comparison-panel.service';
import {ScreenType} from '../../enum/ScreenType';
import {ViewType} from '../../enum/ViewType';
import {BlockConnection} from '../../geometry/block-connection';
import {NodeType} from '../../enum/NodeType';
import {ElementAnalyzer} from '../../helper/element-analyzer';

@Component({
  selector: 'app-screen',
  templateUrl: './screen.component.html'
})
export class ScreenComponent implements OnInit, OnChanges, OnDestroy {

  @Input() screenType: ScreenType;
  @Input() activeViewType: ViewType;
  @Input() activeFilter: IFilter;
  @Input() metricTree: INode;
  @Input() metricMapping: IMetricMapping;

  subscriptions: Subscription[] = [];
  renderer: WebGLRenderer;
  scene: Scene = new Scene();
  // (see https://github.com/nicolaspanel/three-orbitcontrols-ts/issues/1)
  camera: THREE.PerspectiveCamera;
  controls: OrbitControls;
  tooltipLine: Object3D;
  highlightBox: Object3D;
  interactionHandler: InteractionHandler;

  // use THREE.PerspectiveCamera instead of importing PerspectiveCamera to avoid warning for panning and zooming are disabled
  view: AbstractView;
  private isMergedView = false;
  private requestAnimationFrameId: number;
  private renderingIsPaused = false;

  constructor(
    private focusService: FocusService,
    private tooltipService: TooltipService,
    private comparisonPanelService: ComparisonPanelService
  ) {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.activeViewType !== null && this.metricTree !== null && this.activeFilter !== null) {
      this.isMergedView = this.activeViewType === ViewType.MERGED;
      this.interactionHandler.setIsMergedView(this.isMergedView);

      if (this.isMergedView) {
        this.view = new MergedView(this.screenType, this.metricMapping);
        if (this.screenType === ScreenType.RIGHT) {
          this.pauseRendering();
        }
        document.querySelector('#stage').classList.remove('split');

      } else {
        this.view = new SplitView(this.screenType, this.metricMapping);
        if (this.screenType === ScreenType.RIGHT) {
          this.resumeRendering();
        }
        document.querySelector('#stage').classList.add('split');
      }

      this.resetScene();
      this.prepareView(this.metricTree);
      this.applyFilter(this.activeFilter);
      this.handleViewChanged();
    }

    if (
      changes.metricTree
      && changes.metricTree.currentValue
      && ElementAnalyzer.hasMetricValuesForCurrentCommit(
      changes.metricTree.currentValue,
      this.activeViewType === ViewType.MERGED,
      this.screenType
      )
    ) {
      this.resetCamera();
      this.resetControls();
    }
  }

  ngOnInit() {
    this.view = new SplitView(this.screenType, this.metricMapping);

    this.createCamera();
    this.createControls();
    this.createLight();
    this.createRenderer();
    this.createTooltip();
    this.createSelectionHighlightBox();
    this.createInteractionHandler();

    this.initializeEventListeners();

    this.render();

    this.subscriptions.push(
      this.focusService.elementFocussed$.subscribe((elementName) => {
        this.focusElementByName(elementName);
        this.comparisonPanelService.show({
          elementName,
          foundElement: ElementAnalyzer.findElementByName(this.metricTree, elementName)
        });
      })
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription: Subscription) => {
      subscription.unsubscribe();
    });
  }

  createRenderer() {
    this.renderer = new WebGLRenderer({antialias: true, preserveDrawingBuffer: true,logarithmicDepthBuffer:true});
    this.renderer.setClearColor(0xf0f0f0);
    this.renderer.setSize(this.getScreenWidth() - 0, window.innerHeight);

    document.querySelector('#stage').appendChild(this.renderer.domElement);
  }

  updateRenderer() {
    this.renderer.setSize(this.getScreenWidth() - 0, window.innerHeight);
  }

  createLight() {
    const ambientLight = new THREE.AmbientLight(0xcccccc, 0.5);
    this.scene.add(ambientLight);

    const directionalLight = new THREE.DirectionalLight(0xffffff, 0.4);
    directionalLight.position.set(0, 1, 0);
    this.scene.add(directionalLight);
  }

  createCamera() {
    this.camera = new THREE.PerspectiveCamera(
      45,
      (this.getScreenWidth() - 0) / window.innerHeight,
      VisualizationConfig.CAMERA_NEAR,
      VisualizationConfig.CAMERA_FAR
    );
    this.scene.add(this.camera);
  }

  updateCamera() {
    this.camera.aspect = (this.getScreenWidth() - 0) / window.innerHeight;
    this.camera.updateProjectionMatrix();
  }

  resetCamera() {
    const root = this.getRoot();
    // pythagoras
    const diagonal = Math.sqrt(Math.pow(root.scale.x, 2) + Math.pow(root.scale.z, 2));
    this.camera.position.x = root.scale.x * 2;
    this.camera.position.y = diagonal * 1.5;
    this.camera.position.z = root.scale.z * 2;
  }

  createControls() {
    this.controls = new OrbitControls(this.camera, document.querySelector('#stage'));
  }

  resetControls() {
    const centralCoordinates = this.getCentralCoordinates();
    this.controls.target.x = centralCoordinates.x;
    this.controls.target.y = centralCoordinates.y;
    this.controls.target.z = centralCoordinates.z;
  }

  render() {
    this.requestAnimationFrameId = requestAnimationFrame(() => {
      this.render();
    });

    this.controls.update();
    this.renderer.render(this.scene, this.camera);
    this.interactionHandler.update(this.camera);
    TWEEN.update();
  }

  pauseRendering() {
    if (this.requestAnimationFrameId) {
      cancelAnimationFrame(this.requestAnimationFrameId);
      this.resetScene();
      this.renderingIsPaused = true;
    }
  }

  resumeRendering() {
    if (this.renderingIsPaused) {
      this.render();
      this.renderingIsPaused = false;
    }
  }

  prepareView(metricTree) {
    if (metricTree.children.length === 0) {
      return;
    }
    this.view.setMetricTree(metricTree);
    this.view.recalculate();
    this.view.getBlockElements().forEach((element) => {
      this.scene.add(element);
    });

    if (this.view instanceof MergedView) {
      this.view.calculateConnections(this.scene);
      this.view.getConnections().forEach((blockConnection: BlockConnection) => {
        this.scene.add(blockConnection.getCurve());
      });
    }
  }

  createInteractionHandler() {
    this.interactionHandler = new InteractionHandler(
      this.scene,
      this.renderer,
      this.isMergedView,
      this.focusService,
      this.tooltipService,
      this.tooltipLine,
      this.highlightBox
    );
  }

  resetScene() {
    for (let i = this.scene.children.length - 1; i >= 0; i--) {
      const child = this.scene.children[i];

      // only remove Blocks and Lines. Don't remove lights, cameras etc.
      if (child.type === 'Mesh' || child.type === 'Line') {
        this.scene.remove(child);
      }
    }
  }

  focusElementByName(elementName) {
    const element = this.scene.getObjectByName(elementName);
    if (!element) {
      return;
    }

    const root = this.getRoot();
    // pythagoras
    const diagonal = Math.sqrt(Math.pow(root.scale.x, 2) + Math.pow(root.scale.z, 2));

    new TWEEN.Tween(this.camera.position)
      .to({
        x: element.position.x + root.scale.x / 5,
        y: element.position.y + diagonal / 5,
        z: element.position.z + root.scale.z / 5
      }, VisualizationConfig.CAMERA_ANIMATION_DURATION)
      .easing(TWEEN.Easing.Sinusoidal.InOut)
      .start();

    new TWEEN.Tween(this.controls.target)
      .to({
        x: element.position.x + element.scale.x / 2,
        y: element.position.y,
        z: element.position.z + element.scale.z / 2
      }, VisualizationConfig.CAMERA_ANIMATION_DURATION)
      .easing(TWEEN.Easing.Sinusoidal.InOut)
      .start();
  }

  private getCentralCoordinates() {
    const root = this.getRoot();
    if (!root) {
      console.warn(`no root found in screen #${this.screenType}`);
      return;
    }

    return {
      x: root.scale.x / 2,
      y: 0,
      z: root.scale.z / 2
    };
  }

  private getRoot():Object3D{
    return this.scene.getObjectByName(VisualizationConfig.ROOT_NAME);
  }

  private getScreenWidth() {
    if (this.isMergedView) {
      return window.innerWidth;
    }
    return window.innerWidth / 2;
  }

  private initializeEventListeners() {
    window.addEventListener('resize', this.handleViewChanged.bind(this), false);
  }

  private handleViewChanged() {
    this.updateCamera();
    this.updateRenderer();
  }

  private applyFilter(activeFilter: IFilter) {
    for (let i = this.scene.children.length - 1; i >= 0; i--) {
      const node = this.scene.children[i];
      if (node.userData && (node.userData.type === NodeType.FILE || node.userData.type === NodeType.CONNECTION)) {
        node.visible = true;

        if (
          activeFilter.unmodified === false
          && node.userData.changeTypes
          && node.userData.changeTypes.modified === false
          && node.userData.changeTypes.deleted === false
          && node.userData.changeTypes.added === false
          && node.userData.changeTypes.renamed === false
        ) {
          node.visible = false;
        }

        if (activeFilter.modified === false && node.userData.changeTypes && node.userData.changeTypes.modified === true) {
          node.visible = false;
        }

        if (activeFilter.deleted === false && node.userData.changeTypes && node.userData.changeTypes.deleted === true) {
          node.visible = false;
        }

        if (activeFilter.added === false && node.userData.changeTypes && node.userData.changeTypes.added === true) {
          node.visible = false;
        }

        if (activeFilter.renamed === false && node.userData.changeTypes && node.userData.changeTypes.renamed === true) {
          node.visible = false;
        }
      }
    }
  }

  private createTooltip() {
    var material = new THREE.MeshBasicMaterial({
      color:0xff0000
    });
    var geometry = new THREE.Geometry();
    geometry.vertices.push(
      new Vector3(0,0,0),
      new Vector3(0,-1,0)
    );
    var tipSize = 0.1;
    var tipGeometry = new THREE.SphereGeometry(tipSize,16,16);
    this.tooltipLine = new Line(geometry,material);
    var tooltipLineTip = new Mesh(tipGeometry,material);
    tooltipLineTip.position.setY(-1);
    this.tooltipLine.add(tooltipLineTip);
    this.tooltipLine.type = "TooltipLine";
    this.tooltipLine.visible = false;
    this.tooltipLine.userData.isHelper = true;
    this.scene.add(this.tooltipLine);
  }

  private createSelectionHighlightBox(){
    var material = new THREE.MeshBasicMaterial({
      color:0xffff00,
      opacity:0.5,
      transparent:true
    });
    var geometry = new THREE.BoxGeometry(1,1,1);
    this.highlightBox = new Mesh(geometry,material);
    this.highlightBox.type = "HighlightBox";
    this.highlightBox.visible = false;
    this.highlightBox.userData.isHelper = true;
    this.scene.add(this.highlightBox);
  }

}
