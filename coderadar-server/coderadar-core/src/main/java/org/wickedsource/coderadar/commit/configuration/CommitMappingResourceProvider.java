package org.wickedsource.coderadar.commit.configuration;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.configuration.MappingResourceProvider;

@Component
public class CommitMappingResourceProvider implements MappingResourceProvider {

  @Override
  public Set<String> getMappingResource() {
    Set<String> resources = new HashSet<>();
    resources.add("org/wickedsource/coderadar/commit/domain/Commit.orm.xml");
    resources.add("org/wickedsource/coderadar/commit/domain/CommitToFileAssociation.orm.xml");
    return resources;
  }
}
