package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.factories.entities.FilePatternFactory;

public class ResourceFactory {

	public static ProjectResourceFactory projectResource() {
		return new ProjectResourceFactory();
	}

	public static FilePatternResourceFactory filePatternResource() {
		return new FilePatternResourceFactory();
	}

	public static FilePatternFactory filePattern() {
		return new FilePatternFactory();
	}

	public static AnalyzerConfigurationResourceFactory analyzerConfigurationResource() {
		return new AnalyzerConfigurationResourceFactory();
	}

	public static AnalyzingJobResourceFactory analyzingJobResource() {
		return new AnalyzingJobResourceFactory();
	}

	public static QualityProfileResourceFactory qualityProfileResource() {
		return new QualityProfileResourceFactory();
	}

	public static UserCredentialsResourceFactory userCredentialsResource() {
		return new UserCredentialsResourceFactory();
	}

	public static UserLoginResourceFactory userLoginResource() {
		return new UserLoginResourceFactory();
	}

	public static PasswordChangeResourceFactory passwordChangeResource() {
		return new PasswordChangeResourceFactory();
	}
}
