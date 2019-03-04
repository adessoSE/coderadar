package org.wickedsource.coderadar.commit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wickedsource.coderadar.commit.domain.CommitToFileAssociation;

@Getter
@AllArgsConstructor
public class CommitToFileAssociatedEvent {
  private final CommitToFileAssociation association;
}
