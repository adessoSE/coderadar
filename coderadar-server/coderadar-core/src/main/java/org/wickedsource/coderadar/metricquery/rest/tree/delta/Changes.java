package org.wickedsource.coderadar.metricquery.rest.tree.delta;

public class Changes {

	private boolean renamed;

	private boolean modified;

	private boolean deleted;

	private boolean added;

	public boolean isRenamed() {
		return renamed;
	}

	public void setRenamed(boolean renamed) {
		this.renamed = renamed;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}
}
