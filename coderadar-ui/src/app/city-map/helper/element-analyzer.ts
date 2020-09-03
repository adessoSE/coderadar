import {INode} from '../interfaces/INode';
import {NodeType} from '../enum/NodeType';
import {ScreenType} from '../enum/ScreenType';
import {CommitReferenceType} from '../enum/CommitReferenceType';
import {MetricValue} from '../../model/metric-value';

export class ElementAnalyzer {

  static findElementByName(nodes: any, elementName: string): INode {
    if (!Array.isArray(nodes)) {
      nodes = [nodes];
    }

    let foundElement: INode;

    for (const node of nodes) {
      if (node.name === elementName) {
        foundElement = node;
      }

      // recursion
      if (node.children && node.children.length > 0 && !foundElement) {
        foundElement = this.findElementByName(node.children, elementName);
      }
    }

    return foundElement;
  }

  static generateUniqueElementList(nodes: INode[], uniqueElements: string[] = []) {
    if (!Array.isArray(nodes)) {
      nodes = [nodes];
    }

    for (const node of nodes) {
      if (uniqueElements.indexOf(node.name) < 0) {
        uniqueElements.push(node.name);
      }

      // recursion
      if (node.children && node.children.length > 0) {
        this.generateUniqueElementList(node.children, uniqueElements);
      }
    }

    return uniqueElements;
  }

  static getMaxModuleLevel(node: INode): number {
    let depth = 0;
    if (node.children) {
      node.children.filter(child => child.type === NodeType.MODULE).forEach((childNode) => {
        depth = Math.max(this.getMaxModuleLevel(childNode), depth);
      });
    }
    return 1 + depth;
  }

  static findSmallestAndBiggestMetricValueByMetricName(nodes: INode[], metricName: string): { min: number, max: number } {
    if (typeof nodes !== 'object' || nodes === null) {
      throw new Error('elements is not an object or null!');
    }

    if (!Array.isArray(nodes)) {
      nodes = [nodes];
    }

    let min = Number.MAX_VALUE;
    let max = Number.MIN_VALUE;

    for (const node of nodes) {
      // investigate only FILEs, because only files can have different sizes and colors
      if (node.type === NodeType.FILE) {
        const commit1Metrics = node.commit1Metrics || null;
        const commit2Metrics = node.commit2Metrics || null;

        const big = this.getMaxMetricValueByMetricName(commit1Metrics, commit2Metrics, metricName);
        if (big > max) {
          max = big;
        }

        const small = this.getMinMetricValueByMetricName(commit1Metrics, commit2Metrics, metricName);
        if (small < min) {
          min = small;
        }
      }

      // recursion
      if (node.children && node.children.length > 0) {
        const result = this.findSmallestAndBiggestMetricValueByMetricName(node.children, metricName);
        if (result.max > max) {
          max = result.max;
        }
        if (result.min < min) {
          min = result.min;
        }
      }
    }

    return {
      min,
      max
    };
  }

  static getMinMetricValueByMetricName(commit1Metrics: any, commit2Metrics: any, metricName: string): number {
    if (commit1Metrics === null && commit2Metrics === null) {
      throw new Error(`No metric objects given`);
    }

    if (commit1Metrics === null) {
      return commit2Metrics[metricName];
    } else if (commit2Metrics === null) {
      return commit1Metrics[metricName];
    } else {
      const commit1 = this.getValueFromMetric(commit1Metrics, metricName);
      const commit2 = this.getValueFromMetric(commit2Metrics, metricName);

      return commit1 < commit2 ? commit1 : commit2;
    }
  }

  static getMaxMetricValueByMetricName(commit1Metrics: MetricValue[], commit2Metrics: MetricValue[], metricName: string): number {
    if (commit1Metrics === null && commit2Metrics === null) {
      throw new Error(`No metric objects given`);
    }

    if (commit1Metrics == null || commit1Metrics.length == 0) {
      return this.getValueFromMetric(commit2Metrics, metricName)
    } else if (commit2Metrics == null || commit2Metrics.length == 0) {
      return this.getValueFromMetric(commit1Metrics, metricName);
    } else {
      const commit1 = this.getValueFromMetric(commit1Metrics, metricName);
      const commit2 = this.getValueFromMetric(commit2Metrics, metricName);

      return commit1 > commit2 ? commit1 : commit2;
    }
  }

  static hasChildrenForCurrentCommit(node: INode, isFullScreen: boolean, screenType: ScreenType): boolean {
    let found = false;

    for (const child of node.children) {
      if (this.hasMetricValuesForCurrentCommit(child, isFullScreen, screenType)) {
        found = true;
      }

      // recursion
      if (child.children && child.children.length > 0 && !found) {
        found = this.hasChildrenForCurrentCommit(child, isFullScreen, screenType);
      }
    }

    return found;
  }

  static hasMetricValuesForCurrentCommit(node: INode, isFullScreen: boolean, screenType: ScreenType) {
    // when in fullScreen mode, metrics for at least one commit should be present
    if (isFullScreen) {
      return (node.commit1Metrics != null && Object.keys(node.commit1Metrics).length > 0)
        || (node.commit2Metrics != null && Object.keys(node.commit2Metrics).length > 0)
        ;
    }

    if (screenType === ScreenType.LEFT) {
      return node.commit1Metrics != null && Object.keys(node.commit1Metrics).length > 0;
    } else if (screenType === ScreenType.RIGHT) {
      return node.commit2Metrics != null && Object.keys(node.commit2Metrics).length > 0;
    } else {
      throw new Error(`Unknown screenType ${screenType}!`);
    }
  }

  static getMetricValueOfElementAndCommitReferenceType(
    node: INode,
    metricName: string,
    commitReferenceType: CommitReferenceType,
    screenType: ScreenType
  ): number {
    if (screenType === ScreenType.LEFT) {
      if (commitReferenceType === CommitReferenceType.THIS) {
        return this.getValueFromMetric(node.commit1Metrics, metricName);
      } else if (commitReferenceType === CommitReferenceType.OTHER) {
        return this.getValueFromMetric(node.commit2Metrics, metricName);
      } else {
        throw new Error(`Unknown commitReferenceType ${commitReferenceType}!`);
      }

    } else if (screenType === ScreenType.RIGHT) {
      if (commitReferenceType === CommitReferenceType.THIS) {
        return this.getValueFromMetric(node.commit2Metrics, metricName);
      } else if (commitReferenceType === CommitReferenceType.OTHER) {
        return this.getValueFromMetric(node.commit1Metrics, metricName);
      } else {
        throw new Error(`Unknown commitReferenceType ${commitReferenceType}!`);
      }

    } else {
      throw new Error(`Unknown screenType ${screenType}!`);
    }
  }

  static getValueFromMetric(metrics: MetricValue[], metricName: string) {
    const index = metrics ? Object.values(metrics).findIndex(object => object.metricName === metricName) : -1;
    return index >= 0 ? Number(metrics[index].value) : undefined;
  }

}
