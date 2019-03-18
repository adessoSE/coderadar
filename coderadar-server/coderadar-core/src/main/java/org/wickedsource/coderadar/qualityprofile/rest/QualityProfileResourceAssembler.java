package org.wickedsource.coderadar.qualityprofile.rest;

import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.qualityprofile.domain.MetricDTO;
import org.wickedsource.coderadar.qualityprofile.domain.QualityProfile;
import org.wickedsource.coderadar.qualityprofile.domain.QualityProfileMetric;

public class QualityProfileResourceAssembler
    extends AbstractResourceAssembler<QualityProfile, QualityProfileResource> {

  private Project project;

  public QualityProfileResourceAssembler(Project project) {
    this.project = project;
  }

  @Override
  public QualityProfileResource toResource(QualityProfile entity) {
    QualityProfileResource resource = new QualityProfileResource();
    resource.setProfileName(entity.getName());
    resource.setId(entity.getId());
    for (QualityProfileMetric metric : entity.getMetrics()) {
      MetricDTO metricDTO = new MetricDTO();
      metricDTO.setMetricName(metric.getName());
      metricDTO.setMetricType(metric.getMetricType());
      resource.addMetric(metricDTO);
    }
    return resource;
  }

  public QualityProfile updateEntity(QualityProfileResource resource, QualityProfile profile) {
    profile.setName(resource.getProfileName());
    profile.setProject(project);
    profile.getMetrics().clear();
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
