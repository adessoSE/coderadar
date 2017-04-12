package org.wickedsource.coderadar.graph.domain.commit;

import org.neo4j.ogm.typeconversion.AttributeConverter;

public class CommitNameConverter implements AttributeConverter<CommitName, String> {
  @Override
  public String toGraphProperty(CommitName value) {
    return value.getValue();
  }

  @Override
  public CommitName toEntityAttribute(String value) {
    return CommitName.from(value);
  }
}
