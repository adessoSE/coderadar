package org.wickedsource.coderadar.metric.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class MetricResourceAssembler extends ResourceAssemblerSupport<String, MetricResource> {

  public MetricResourceAssembler() {
    super(MetricsController.class, MetricResource.class);
  }

  @Override
  public MetricResource toResource(String metricName) {
    return new MetricResource(metricName);
  }
}
