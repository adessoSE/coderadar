package org.wickedsource.coderadar.project.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.analyzerconfig.rest.AnalyzerConfigurationController;
import org.wickedsource.coderadar.analyzingjob.rest.AnalyzingJobController;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.filepattern.rest.FilePatternController;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.VcsCoordinates;

@Component
public class ProjectResourceAssembler extends AbstractResourceAssembler<Project, ProjectResource> {

	public ProjectResourceAssembler() {
		super(ProjectController.class, ProjectResource.class);
	}

	@Override
	public ProjectResource toResource(Project project) {
		ProjectResource resource = createResourceWithId(project.getId(), project);
		resource.setName(project.getName());
		if (project.getVcsCoordinates() != null) {
			resource.setVcsUrl(project.getVcsCoordinates().getUrl().toString());
			resource.setVcsUser(project.getVcsCoordinates().getUsername());
			resource.setVcsPassword(project.getVcsCoordinates().getPassword());
			resource.setStartDate(
					Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(
							project.getVcsCoordinates().getStartDate()));
			resource.setEndDate(
					Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(
							project.getVcsCoordinates().getEndDate()));
			resource.setVcsOnline(project.getVcsCoordinates().isOnline());
		}
		resource.add(
				linkTo(methodOn(FilePatternController.class).getFilePatterns(project.getId()))
						.withRel("files"));
		resource.add(
				linkTo(
								methodOn(AnalyzerConfigurationController.class)
										.getAnalyzerConfigurationsForProject(project.getId()))
						.withRel("analyzers"));
		resource.add(
				linkTo(methodOn(AnalyzingJobController.class).getAnalyzingJob(project.getId()))
						.withRel("strategy"));
		return resource;
	}

	Project updateEntity(ProjectResource resource, Project entity) {
		try {
			entity.setName(resource.getName());
			VcsCoordinates vcs = new VcsCoordinates(new URL(resource.getVcsUrl()));
			vcs.setUsername(resource.getVcsUser());
			vcs.setPassword(resource.getVcsPassword());
			vcs.setStartDate(
					Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(resource.getStartDate()));
			vcs.setEndDate(
					Jsr310Converters.LocalDateToDateConverter.INSTANCE.convert(resource.getEndDate()));
			vcs.setOnline(resource.isVcsOnline());
			entity.setVcsCoordinates(vcs);
			return entity;
		} catch (MalformedURLException e) {
			throw new IllegalStateException("Invalid URL should have been caught earlier!", e);
		}
	}
}
