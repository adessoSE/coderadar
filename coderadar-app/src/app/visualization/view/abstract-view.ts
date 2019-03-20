import {INode} from '../../interfaces/INode';
import {IPackerElement} from '../../interfaces/IPackerElement';
import {AppConfig} from '../../AppConfig';
import {BoxGeometry, Mesh, MeshLambertMaterial, Geometry} from 'three';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {ScreenType} from '../../model/enum/ScreenType';
import {NodeType} from '../../model/enum/NodeType';
import {CommitReferenceType} from '../../model/enum/CommitReferenceType';
import {ElementAnalyzer} from '../../helper/element-analyzer';

declare var GrowingPacker: any;

export abstract class AbstractView {

    rootNode: INode;
    blockElements: Mesh[] = [];
    packer = new GrowingPacker();

    minModuleLevel = 1;
    maxModuleLevel: number;

    geometry: Geometry;

    constructor(protected screenType: ScreenType, protected metricMapping: IMetricMapping) {
        this.geometry = new BoxGeometry(1, 1, 1);
        this.geometry.translate(0.5, 0.5, 0.5);
    }

    setMetricTree(root: INode) {
        this.rootNode = root;
    }

    recalculate() {
        if (!this.rootNode) {
            throw new Error(`rootNode is not defined!`);
        }

        this.maxModuleLevel = ElementAnalyzer.getMaxModuleLevel(this.rootNode);

        this.calculateGroundAreas([this.rootNode]);
        this.calculateElements([this.rootNode], null, 0);
    }

    calculateGroundAreas(nodes: INode[]) {
        if (!Array.isArray(nodes)) {
            nodes = [nodes];
        }

        for (const node of nodes) {
            const element: IPackerElement = { w: 0, h: 0 };

            if (node.type === NodeType.FILE) {
                const edgeLength = this.getEdgeLength(node.commit1Metrics, node.commit2Metrics);
                if (!edgeLength) {
                    element.w = element.h = 0;
                } else {
                    element.w = edgeLength * AppConfig.EDGE_LENGTH_FACTOR + 2 * AppConfig.BLOCK_SPACING;
                    element.h = edgeLength * AppConfig.EDGE_LENGTH_FACTOR + 2 * AppConfig.BLOCK_SPACING;
                }
            }

            // recursion
            if (node.children && node.children.length > 0) {
                const result = this.calculateGroundAreas(node.children);
                element.w = result.w + 2 * AppConfig.BLOCK_SPACING;
                element.h = result.h + 2 * AppConfig.BLOCK_SPACING;
            }

            node.packerInfo = element;
        }

        nodes.sort((a, b) => {
            return b.packerInfo.w - a.packerInfo.w;
        });

        this.packer.fit(nodes.map(node => node.packerInfo));
        return {
            packer: this.packer.root,
            w: this.packer.root.w,
            h: this.packer.root.h
        };
    }

    abstract calculateElements(nodes: INode[], parent: INode, bottom: number);

    createBlock(
        node: INode,
        parent: INode,
        color: any,
        edgeLength: number,
        bottom: number,
        height: number,
        isTransparent: boolean,
        metrics?: any,
        commitType?: CommitReferenceType,
        changeTypes?: any
    ) {
        let finalX;
        let finalY;
        let finalZ;
        let finalWidth;
        let finalHeight;
        let finalDepth;

        const cube = this.createCubeGeometry(color, isTransparent, node.name);
        finalX = node.packerInfo.fit.x + (parent ? parent.packerInfo.renderedX : 0) + AppConfig.BLOCK_SPACING;
        finalY = bottom;
        finalZ = node.packerInfo.fit.y + (parent ? parent.packerInfo.renderedY : 0) + AppConfig.BLOCK_SPACING;

        // save the rendered positions to draw children relative to their parent
        node.packerInfo.renderedX = finalX;
        node.packerInfo.renderedY = finalZ;

        finalWidth = node.type === NodeType.FILE ? edgeLength : node.packerInfo.w - 2 * AppConfig.BLOCK_SPACING;
        finalHeight = height;
        finalDepth = node.type === NodeType.FILE ? edgeLength : node.packerInfo.h - 2 * AppConfig.BLOCK_SPACING;

        cube.position.x = finalX;
        cube.position.y = finalY;
        cube.position.z = finalZ;

        cube.scale.x = finalWidth;
        cube.scale.y = finalHeight;
        cube.scale.z = finalDepth;

        cube.userData = this.createUserData(node, parent, bottom, isTransparent, metrics, commitType, changeTypes);

        this.blockElements.push(cube);
    }

    createCubeGeometry(color: string, isTransparent: boolean, name: string): Mesh {
        const material = new MeshLambertMaterial({color});

        if (isTransparent) {
            material.transparent = true;
            material.opacity = 0.4;
        }

        const block = new Mesh(this.geometry, material);
        block.name = name;
        return block;
    }

    createUserData(
        node: INode,
        parent: INode,
        bottom: number,
        isTransparent: boolean,
        metrics: any,
        commitType?: CommitReferenceType,
        changeTypes?: any
    ) {
        return {
            parentName: parent ? parent.name : undefined,
            bottom,
            metrics,
            type: node.type,
            elementName: node.name,
            isHelper: isTransparent,
            commitType,
            changeTypes
        };
    }

    getBlockElements(): Mesh[] {
        return this.blockElements;
    }

    private getEdgeLength(commit1Metrics: any, commit2Metrics: any): number {
        const groundAreaValue = ElementAnalyzer.getMaxMetricValueByMetricName(
            commit1Metrics,
            commit2Metrics,
            this.metricMapping.groundAreaMetricName
        );
        return Math.sqrt(groundAreaValue);
    }
}
