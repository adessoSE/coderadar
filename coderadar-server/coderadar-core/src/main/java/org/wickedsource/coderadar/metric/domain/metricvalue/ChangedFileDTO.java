package org.wickedsource.coderadar.metric.domain.metricvalue;

import org.wickedsource.coderadar.analyzer.api.ChangeType;

public class ChangedFileDTO {

	private final String oldFileName;

	private final String newFileName;

	private final ChangeType changeType;

	public ChangedFileDTO(String oldFileName, String newFileName, ChangeType changeType) {
		this.oldFileName = oldFileName;
		this.newFileName = newFileName;
		this.changeType = changeType;
	}

	public String getOldFileName() {
		return oldFileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public ChangeType getChangeType() {
		return changeType;
	}
}
