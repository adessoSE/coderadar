import {VisualizationConfig} from '../VisualizationConfig';
import {Color} from 'three';
import {ScreenType} from '../enum/ScreenType';

declare var chroma: any;

export class ColorHelper {

  static getColorByPosition(screenType: ScreenType): Color {
    return new Color(screenType === ScreenType.LEFT ? VisualizationConfig.COLOR_FIRST_COMMIT : VisualizationConfig.COLOR_SECOND_COMMIT);
  }

  static colorFromHex(color:string):Color{
    return new Color(color);
  }

  static hexFromColor(color:Color):string{
    return color.getHexString();
  }

  static getContraryColorByColor(color: Color): Color {
    return ColorHelper.colorFromHex(ColorHelper.hexFromColor(color) === VisualizationConfig.COLOR_FIRST_COMMIT ? VisualizationConfig.COLOR_SECOND_COMMIT : VisualizationConfig.COLOR_FIRST_COMMIT);
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
