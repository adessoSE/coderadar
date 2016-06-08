package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.repository.CrudRepository;

public interface CommitToFileAssociationRepository extends CrudRepository<CommitToFileAssociation, CommitToFileId> {
}
