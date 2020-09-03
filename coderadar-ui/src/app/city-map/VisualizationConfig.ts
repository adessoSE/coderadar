import {IMetric} from './interfaces/IMetric';

export class VisualizationConfig {

  // VISUALIZATION SETTINGS
  static EDGE_LENGTH_FACTOR = 2;
  static ELEMENT_HEIGHT_FACTOR = 0.1;
  static ELEMENT_MINIMUM_HEIGHT = 1;
  static ROOT_NAME = "root";
  // static GLOBAL_MAX_GROUND_AREA = 100;
  // static GLOBAL_MIN_GROUND_AREA = 1;
  // static GLOBAL_MAX_HEIGHT = 100;
  // static GLOBAL_MIN_HEIGHT = 1;
  static BLOCK_SPACING = 5;
  static MODULE_BLOCK_HEIGHT = 5;

  // CAMERA SETTINGS
  static CAMERA_NEAR = 5;
  static CAMERA_FAR = 10000;
  static CAMERA_DISTANCE_TO_FOCUSSED_ELEMENT = 300;
  static CAMERA_ANIMATION_DURATION = 1500;

  // COLORS
  static COLOR_HIERARCHY_RANGE: string[] = ['#cccccc', '#525252'];
  static COLOR_HEATMAP_RANGE: string[] = ['#ffffff', '#ffc905', '#f78400', '#e92100', '#9b1909', '#4f1609', '#5d0000'];
  static COLOR_CONNECTION = '#000000';

  static COLOR_FIRST_COMMIT = '#0e8cf3';
  static COLOR_SECOND_COMMIT = '#ffb100';

  static COLOR_ADDED_FILE = '#49c35c';
  static COLOR_DELETED_FILE = '#d90206';
  static COLOR_UNCHANGED_FILE = '#cccccc';

  // HIGHLIGHTING
  static HIGHLIGHT_BOX_MARGIN = 0.25;

  static getShortNameByMetricName(metricName: string): IMetric {
    const shortname = metricName.split('.').pop();
    return {
      metricName,
      shortName: shortname
    };
  }
}
