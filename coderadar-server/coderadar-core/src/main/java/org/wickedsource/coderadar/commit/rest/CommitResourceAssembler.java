package org.wickedsource.coderadar.commit.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.project.rest.ProjectController;

public class CommitResourceAssembler extends AbstractResourceAssembler<Commit, CommitResource> {

	private long projectId;

	public CommitResourceAssembler(long projectId) {
		super(CommitController.class, CommitResource.class);
		this.projectId = projectId;
	}

	@Override
	public CommitResource toResource(Commit entity) {
		CommitResource resource = new CommitResource();
		resource.setAnalyzed(entity.isAnalyzed());
		resource.setAuthor(entity.getAuthor());
		resource.setName(entity.getName());
		resource.setTimestamp(entity.getTimestamp());
		resource.add(
				linkTo(methodOn(ProjectController.class).getProject(this.projectId)).withRel("project"));
		return resource;
	}
}
