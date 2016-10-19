package org.wickedsource.coderadar.metricquery.rest.module;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValuePerModuleDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetricsTreeResourceAssembler extends ResourceAssemblerSupport<List<MetricValuePerModuleDTO>, MetricsTreeResource> {

    public MetricsTreeResourceAssembler() {
        super(ModuleMetricsController.class, MetricsTreeResource.class);
    }

    @Override
    public MetricsTreeResource toResource(List<MetricValuePerModuleDTO> metricValuesPerModule) {
        Map<String, List<MetricValuePerModuleDTO>> metricValuesGroupedByModule = metricValuesPerModule
                .stream()
                .collect(Collectors.groupingBy(MetricValuePerModuleDTO::getModule));
        MetricsTreeResource metricsTreeResource = new MetricsTreeResource();
        metricsTreeResource.addModules(metricValuesGroupedByModule.keySet(), (module -> toModuleMetricsResource(metricValuesGroupedByModule.get(module))));
        return metricsTreeResource;
    }

    private MetricValuesSet toModuleMetricsResource(List<MetricValuePerModuleDTO> metricValuesPerModule) {
        MetricValuesSet moduleMetrics = new MetricValuesSet();
        for (MetricValuePerModuleDTO metricValuePerModuleDTO : metricValuesPerModule) {
            moduleMetrics.setMetricValue(metricValuePerModuleDTO.getMetric(), metricValuePerModuleDTO.getValue());
        }
        return moduleMetrics;
    }

}
