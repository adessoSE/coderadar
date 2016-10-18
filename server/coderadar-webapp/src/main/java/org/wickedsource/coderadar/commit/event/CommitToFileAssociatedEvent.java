package org.wickedsource.coderadar.commit.event;

import org.wickedsource.coderadar.commit.domain.CommitToFileAssociation;

public class CommitToFileAssociatedEvent {

    private final CommitToFileAssociation association;

    public CommitToFileAssociatedEvent(CommitToFileAssociation association) {
        this.association = association;
    }

    public CommitToFileAssociation getAssociation() {
        return association;
    }
}
