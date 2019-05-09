package io.reflectoring.coderadar.core.vcs.port.driven;

import org.eclipse.jgit.api.Git;

import java.io.File;

public interface CloneRepositoryPort {
    Git cloneRepository(String remoteUrl, File localDir);
}
