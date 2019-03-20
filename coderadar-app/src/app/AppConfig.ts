import {IMetric} from './city-map/interfaces/IMetric';

export class AppConfig {
    // CODERADAR SERVER CONFIG
    static BASE_URL = 'http://localhost:8080';

    // METRIC NAME MAPPING
    static METRIC_NAME_MAPPING: IMetric[] = [
        { shortName: 'Lines of Code (LOC)', metricName: 'coderadar:size:loc:java' },
        { shortName: 'Comment Lines of Code (CLOC)', metricName: 'coderadar:size:cloc:java' },
        { shortName: 'Source Lines of Code (SLOC)', metricName: 'coderadar:size:sloc:java' },
        { shortName: 'Effective Lines of Code (ELOC)', metricName: 'coderadar:size:eloc:java' },
        { shortName: 'MagicNumber', metricName: 'checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck' },
        { shortName: 'ReturnCount', metricName: 'checkstyle:com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck' },
        // tslint:disable-next-line:max-line-length
        { shortName: 'CyclomaticComplexity', metricName: 'checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck' },
        { shortName: 'JavaNCSS', metricName: 'checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck' },
        { shortName: 'NPathComplexity', metricName: 'checkstyle:com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck' },
        // tslint:disable-next-line:max-line-length
        { shortName: 'ExecutableStatementCount', metricName: 'checkstyle:com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck' }
    ];

    // VISUALIZATION SETTINGS
    static EDGE_LENGTH_FACTOR = 2;
    static HEIGHT_FACTOR = 0.1;
    // static GLOBAL_MAX_GROUND_AREA = 100;
    // static GLOBAL_MIN_GROUND_AREA = 1;
    // static GLOBAL_MAX_HEIGHT = 100;
    // static GLOBAL_MIN_HEIGHT = 1;
    static BLOCK_SPACING = 5;
    static MODULE_BLOCK_HEIGHT = 5;

    // CAMERA SETTINGS
    static CAMERA_NEAR = 0.1;
    static CAMERA_FAR = 100000;
    static CAMERA_DISTANCE_TO_FOCUSSED_ELEMENT = 100;
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

    static getShortNameByMetricName(metricName: string): IMetric {
        return this.METRIC_NAME_MAPPING.find(namePair => namePair.metricName === metricName);
    }
}
