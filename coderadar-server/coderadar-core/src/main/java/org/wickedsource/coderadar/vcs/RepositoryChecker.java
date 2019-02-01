package org.wickedsource.coderadar.vcs;

import java.nio.file.Path;

public interface RepositoryChecker {

	/** Determines whether the given folder is the root folder of a local copy of a vcs repository. */
	boolean isRepository(Path folder);
}
