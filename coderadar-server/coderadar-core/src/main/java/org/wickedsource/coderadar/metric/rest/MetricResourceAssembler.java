package org.wickedsource.coderadar.metric.rest;

import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;

public class MetricResourceAssembler extends AbstractResourceAssembler<String, MetricResource> {

  @Override
  public MetricResource toResource(String metricName) {
    return new MetricResource(metricName);
  }
}
