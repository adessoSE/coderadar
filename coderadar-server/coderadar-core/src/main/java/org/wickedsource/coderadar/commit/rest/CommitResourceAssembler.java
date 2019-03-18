package org.wickedsource.coderadar.commit.rest;

import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;

public class CommitResourceAssembler extends AbstractResourceAssembler<Commit, CommitResource> {
  @Override
  public CommitResource toResource(Commit entity) {
    CommitResource resource = new CommitResource();
    resource.setAnalyzed(entity.isAnalyzed());
    resource.setAuthor(entity.getAuthor());
    resource.setName(entity.getName());
    resource.setTimestamp(entity.getTimestamp());
    return resource;
  }
}
