package org.wickedsource.coderadar.metric.configuration;

import java.util.Collections;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.configuration.MappingResourceProvider;

@Component
public class MetricValueMappingResourceProvider implements MappingResourceProvider {

  @Override
  public Set<String> getMappingResource() {
    return Collections.singleton(
        "org/wickedsource/coderadar/metric/domain/metricvalue/MetricValue.orm.xml");
  }
}
