import {VisualizationConfig} from '../VisualizationConfig';
import {Color} from 'three';
import {ScreenType} from '../../model/enum/ScreenType';

declare var chroma: any;

export class ColorHelper {

    static getColorByPosition(screenType: ScreenType): string {
        return screenType === ScreenType.LEFT ? VisualizationConfig.COLOR_FIRST_COMMIT : VisualizationConfig.COLOR_SECOND_COMMIT;
    }

    static getContraryColorByColor(color: string): string {
        return color === VisualizationConfig.COLOR_FIRST_COMMIT ? VisualizationConfig.COLOR_SECOND_COMMIT : VisualizationConfig.COLOR_FIRST_COMMIT;
    }

    static getColorByMetricValue(value: number, max: number, min: number): Color {
        return this.getColorScale(VisualizationConfig.COLOR_HEATMAP_RANGE, value, max, min);
    }

    static getColorByLevelValue(value: number, max: number, min: number): Color {
        return this.getColorScale(VisualizationConfig.COLOR_HIERARCHY_RANGE, value, max, min);
    }

    static getColorScale(range, value: number, max: number, min: number): Color {
        const colorScale = chroma.scale(range);
        const hexValue = colorScale(value / (max + min)).hex();
        return new Color(hexValue);
    }

}
