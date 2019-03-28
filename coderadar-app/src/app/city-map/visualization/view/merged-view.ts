import {AbstractView} from './abstract-view';
import {INode} from '../../interfaces/INode';
import {VisualizationConfig} from '../../VisualizationConfig';
import {Scene} from 'three';
import {IMetricMapping} from '../../interfaces/IMetricMapping';
import {BlockConnection} from '../../geometry/block-connection';
import {ScreenType} from '../../enum/ScreenType';
import {CommitReferenceType} from '../../enum/CommitReferenceType';
import {NodeType} from '../../enum/NodeType';
import {ColorHelper} from '../../helper/color-helper';
import {ElementAnalyzer} from '../../helper/element-analyzer';

export class MergedView extends AbstractView {

  movedElements: any[] = [];
  connections: BlockConnection[] = [];

  constructor(screenType: ScreenType, metricMapping: IMetricMapping) {
    super(screenType, metricMapping);
  }

  calculateElements(nodes: INode[], parent: INode, bottom: number, level: number = 1) {
    if (!Array.isArray(nodes)) {
      nodes = [nodes];
    }

    nodes.forEach((node) => {
      if (!node.packerInfo.fit) {
        console.warn(`node ${node.name} at position ${this.screenType} has no fit!`);
        return;
      }

      let blueHeight;

      // FILE
      if (node.type === NodeType.FILE) {
        const blueHeightMetric = ElementAnalyzer.getMetricValueOfElementAndCommitReferenceType(
          node, this.metricMapping.heightMetricName, CommitReferenceType.THIS, this.screenType
        );
        const orangeHeightMetric = ElementAnalyzer.getMetricValueOfElementAndCommitReferenceType(
          node, this.metricMapping.heightMetricName, CommitReferenceType.OTHER, this.screenType
        );

        const blueGroundAreaMetric = ElementAnalyzer.getMetricValueOfElementAndCommitReferenceType(
          node, this.metricMapping.groundAreaMetricName, CommitReferenceType.THIS, this.screenType
        );
        const orangeGroundAreaMetric = ElementAnalyzer.getMetricValueOfElementAndCommitReferenceType(
          node, this.metricMapping.groundAreaMetricName, CommitReferenceType.OTHER, this.screenType
        );

        const blueColorMetric = ElementAnalyzer.getMetricValueOfElementAndCommitReferenceType(
          node, this.metricMapping.colorMetricName, CommitReferenceType.THIS, this.screenType
        );
        const orangeColorMetric = ElementAnalyzer.getMetricValueOfElementAndCommitReferenceType(
          node, this.metricMapping.colorMetricName, CommitReferenceType.OTHER, this.screenType
        );

        const blueMetrics = {
          [this.metricMapping.heightMetricName]: blueHeightMetric,
          [this.metricMapping.groundAreaMetricName]: blueGroundAreaMetric,
          [this.metricMapping.colorMetricName]: blueColorMetric
        };

        const orangeMetrics = {
          [this.metricMapping.heightMetricName]: orangeHeightMetric,
          [this.metricMapping.groundAreaMetricName]: orangeGroundAreaMetric,
          [this.metricMapping.colorMetricName]: orangeColorMetric
        };

        blueHeight = blueHeightMetric * VisualizationConfig.HEIGHT_FACTOR;
        const orangeHeight = orangeHeightMetric * VisualizationConfig.HEIGHT_FACTOR;

        const blueEdgeLength = Math.sqrt(blueGroundAreaMetric) * VisualizationConfig.EDGE_LENGTH_FACTOR;
        const orangeEdgeLength = Math.sqrt(orangeGroundAreaMetric) * VisualizationConfig.EDGE_LENGTH_FACTOR;

        const blueColor = ColorHelper.getColorByPosition(this.screenType);
        const orangeColor = ColorHelper.getContraryColorByColor(blueColor);

        const blueTransparency = blueHeight >= orangeHeight && blueEdgeLength >= orangeEdgeLength;
        const orangeTransparency = orangeHeight >= blueHeight && orangeEdgeLength >= blueEdgeLength;

        if (!isNaN(blueEdgeLength) && !isNaN(orangeEdgeLength)) {
          // both blocks
          if (blueEdgeLength < orangeEdgeLength) {
            // draw the bigger block ...
            this.createBlock(
              node,
              parent,
              orangeColor,
              orangeEdgeLength,
              bottom,
              orangeHeight,
              orangeTransparency,
              orangeMetrics,
              CommitReferenceType.OTHER,
              node.changes
            );

            // ... calculate the center position for the smaller block ...
            node.packerInfo.fit.x += (orangeEdgeLength - blueEdgeLength) / 2;
            node.packerInfo.fit.y += (orangeEdgeLength - blueEdgeLength) / 2;

            // ... draw the smaller block
            this.createBlock(
              node,
              parent,
              blueColor,
              blueEdgeLength,
              bottom,
              blueHeight,
              blueTransparency,
              blueMetrics,
              CommitReferenceType.THIS,
              node.changes
            );
          } else if (blueEdgeLength > orangeEdgeLength) {
            // draw the bigger block ...
            this.createBlock(
              node,
              parent,
              blueColor,
              blueEdgeLength,
              bottom,
              blueHeight,
              blueTransparency,
              blueMetrics,
              CommitReferenceType.THIS,
              node.changes
            );

            // ... calculate the center position for the smaller block ...
            node.packerInfo.fit.x += (blueEdgeLength - orangeEdgeLength) / 2;
            node.packerInfo.fit.y += (blueEdgeLength - orangeEdgeLength) / 2;

            // ... draw the smaller block
            this.createBlock(
              node,
              parent,
              orangeColor,
              orangeEdgeLength,
              bottom,
              orangeHeight,
              orangeTransparency,
              orangeMetrics,
              CommitReferenceType.OTHER,
              node.changes
            );
          } else {
            // ground areas are the same
            if (blueHeight !== orangeHeight) {
              // heights are different, so draw both blocks
              this.createBlock(
                node,
                parent,
                blueColor,
                blueEdgeLength,
                bottom,
                blueHeight,
                blueTransparency,
                blueMetrics,
                CommitReferenceType.THIS,
                node.changes
              );
              this.createBlock(
                node,
                parent,
                orangeColor,
                orangeEdgeLength,
                bottom,
                orangeHeight,
                orangeTransparency,
                orangeMetrics,
                CommitReferenceType.OTHER,
                node.changes
              );
            } else {
              // heights are the same, so the file has not changed
              this.createBlock(
                node,
                parent,
                VisualizationConfig.COLOR_UNCHANGED_FILE,
                orangeEdgeLength,
                bottom,
                orangeHeight,
                false,
                orangeMetrics,
                undefined,
                node.changes
              );
            }
          }

        } else if (isNaN(orangeEdgeLength)) {
          // only blue block

          // cache element to draw connections
          if (this.isNodeMoved(node)) {
            this.movedElements.push({
              fromElementName: node.name,
              toElementName: node.renamedTo
            });
          }

          this.createBlock(
            node,
            parent,
            VisualizationConfig.COLOR_DELETED_FILE,
            blueEdgeLength,
            bottom,
            blueHeight,
            false,
            blueMetrics,
            CommitReferenceType.THIS,
            node.changes
          );

        } else if (isNaN(blueEdgeLength)) {
          // only orange block

          this.createBlock(
            node,
            parent,
            VisualizationConfig.COLOR_ADDED_FILE,
            orangeEdgeLength,
            bottom,
            orangeHeight,
            false,
            orangeMetrics,
            CommitReferenceType.OTHER,
            node.changes
          );
        }

        // MODULE
      } else {
        // don't draw empty modules
        if (ElementAnalyzer.hasChildrenForCurrentCommit(node, true, this.screenType)) {
          blueHeight = VisualizationConfig.MODULE_BLOCK_HEIGHT;
          const moduleColor = ColorHelper.getColorByLevelValue(level, this.minModuleLevel, this.maxModuleLevel);
          this.createBlock(node, parent, moduleColor, undefined, bottom, blueHeight, false);
        }
      }

      // recursion
      if (node.children && node.children.length > 0) {
        this.calculateElements(node.children, node, bottom + blueHeight, level + 1);
      }
    });
  }

  calculateConnections(scene: Scene) {
    for (const movedElementPair of this.movedElements) {
      const fromElement = scene.getObjectByName(movedElementPair.fromElementName);
      const toElement = scene.getObjectByName(movedElementPair.toElementName);

      if (fromElement && toElement) {
        this.connections.push(new BlockConnection(fromElement, toElement));
      } else {
        console.warn(`A connection could not be drawn because at least one element could not be found in the scene.`);
      }
    }
  }

  getConnections(): BlockConnection[] {
    return this.connections;
  }

  private isNodeMoved(node: INode) {
    return node.renamedTo != null || node.renamedFrom != null;
  }
}
