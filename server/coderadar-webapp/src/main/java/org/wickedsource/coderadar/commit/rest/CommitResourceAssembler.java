package org.wickedsource.coderadar.commit.rest;

import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;

@Component
public class CommitResourceAssembler extends AbstractResourceAssembler<Commit, CommitResource> {

    public CommitResourceAssembler() {
        super(CommitController.class, CommitResource.class);
    }

    @Override
    public CommitResource toResource(Commit entity) {
        CommitResource resource = new CommitResource();
        resource.setAnalyzed(entity.isAnalyzed());
        resource.setAuthor(entity.getAuthor());
        resource.setName(entity.getName());
        resource.setParentCommitName(entity.getParentCommitName());
        resource.setTimestamp(entity.getTimestamp());
        return resource;
    }

}
