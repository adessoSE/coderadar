package org.wickedsource.coderadar.analyzerconfig.rest;

import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.project.domain.Project;

public class AnalyzerConfigurationResourceAssembler
    extends AbstractResourceAssembler<AnalyzerConfiguration, AnalyzerConfigurationResource> {

  private Long projectId;

  AnalyzerConfigurationResourceAssembler(Long projectId) {
    this.projectId = projectId;
  }

  @Override
  public AnalyzerConfigurationResource toResource(AnalyzerConfiguration entity) {
    return new AnalyzerConfigurationResource(
        entity.getId(), entity.getAnalyzerName(), entity.getEnabled());
  }

  public AnalyzerConfiguration toEntity(AnalyzerConfigurationResource resource, Project project) {
    return updateEntity(resource, project, new AnalyzerConfiguration());
  }

  public AnalyzerConfiguration updateEntity(
      AnalyzerConfigurationResource resource, Project project, AnalyzerConfiguration entity) {
    entity.setAnalyzerName(resource.getAnalyzerName());
    entity.setEnabled(resource.isEnabled());
    entity.setProject(project);
    return entity;
  }
}
