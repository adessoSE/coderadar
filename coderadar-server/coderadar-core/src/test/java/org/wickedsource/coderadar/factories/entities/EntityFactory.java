package org.wickedsource.coderadar.factories.entities;

public class EntityFactory {

	public static ProjectFactory project() {
		return new ProjectFactory();
	}

	public static JobFactory job() {
		return new JobFactory();
	}

	public static CommitFactory commit() {
		return new CommitFactory();
	}

	public static SourceFileFactory sourceFile() {
		return new SourceFileFactory();
	}

	public static AnalyzerConfigurationFactory analyzerConfiguration() {
		return new AnalyzerConfigurationFactory();
	}

	public static AnalyzerFactory analyzer() {
		return new AnalyzerFactory();
	}

	public static AnalyzingJobFactory analyzingJob() {
		return new AnalyzingJobFactory();
	}

	public static UserFactory user() {
		return new UserFactory();
	}
}
