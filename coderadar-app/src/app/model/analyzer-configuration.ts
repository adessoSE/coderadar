export class AnalyzerConfiguration {
  id: number;
  analyzerName: string;
  enabled: boolean;

  constructor(name: string, enabled: boolean) {
    this.analyzerName = name;
    this.enabled = enabled;
  }

}
