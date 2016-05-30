package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.repository.CrudRepository;

public interface CommitToSourceFileAssociationRepository extends CrudRepository<CommitToFileAssociation, CommitToFileId> {
}
