export class AnalyzerConfiguration {
  analyzerName: string;
  enabled: boolean;

  constructor(name: string, enabled: boolean) {
    this.analyzerName = name;
    this.enabled = enabled;
  }

}
