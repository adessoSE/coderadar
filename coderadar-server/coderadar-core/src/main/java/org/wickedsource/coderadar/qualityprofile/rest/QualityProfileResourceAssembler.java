package org.wickedsource.coderadar.qualityprofile.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;
import org.wickedsource.coderadar.qualityprofile.domain.MetricDTO;
import org.wickedsource.coderadar.qualityprofile.domain.QualityProfile;
import org.wickedsource.coderadar.qualityprofile.domain.QualityProfileMetric;

public class QualityProfileResourceAssembler
    extends ResourceAssemblerSupport<QualityProfile, QualityProfileResource> {

  private Project project;

  public QualityProfileResourceAssembler(Project project) {
    super(QualityProfileController.class, QualityProfileResource.class);
    this.project = project;
  }

  @Override
  public QualityProfileResource toResource(QualityProfile entity) {
    QualityProfileResource resource = new QualityProfileResource();
    resource.setProfileName(entity.getName());
    for (QualityProfileMetric metric : entity.getMetrics()) {
      MetricDTO metricDTO = new MetricDTO();
      metricDTO.setMetricName(metric.getName());
      metricDTO.setMetricType(metric.getMetricType());
      resource.addMetric(metricDTO);
    }
    resource.add(
        linkTo(
                methodOn(QualityProfileController.class)
                    .getQualityProfile(entity.getId(), project.getId()))
            .withRel("self"));
    resource.add(
        linkTo(methodOn(ProjectController.class).getProject(project.getId())).withRel("project"));
    return resource;
  }

  public QualityProfile toEntity(QualityProfileResource resource) {
    QualityProfile profile = new QualityProfile();
    profile.setName(resource.getProfileName());
    profile.setProject(project);
    for (MetricDTO metricDTO : resource.getMetrics()) {
      QualityProfileMetric metric = new QualityProfileMetric();
      metric.setName(metricDTO.getMetricName());
      metric.setMetricType(metricDTO.getMetricType());
      metric.setProfile(profile);
      profile.getMetrics().add(metric);
    }
    return profile;
  }
}
