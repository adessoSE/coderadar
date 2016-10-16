package org.wickedsource.coderadar.metricquery.rest.module;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValuePerModuleDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModuleMetricsTreeResourceAssembler extends ResourceAssemblerSupport<List<MetricValuePerModuleDTO>, ModuleTreeResource> {

    public ModuleMetricsTreeResourceAssembler() {
        super(ModuleMetricsController.class, ModuleTreeResource.class);
    }

    @Override
    public ModuleTreeResource toResource(List<MetricValuePerModuleDTO> metricValuesPerModule) {
        Map<String, List<MetricValuePerModuleDTO>> metricValuesGroupedByModule = metricValuesPerModule
                .stream()
                .collect(Collectors.groupingBy(MetricValuePerModuleDTO::getModule));
        ModuleTreeResource moduleTreeResource = new ModuleTreeResource();
        moduleTreeResource.addModules(metricValuesGroupedByModule.keySet(), (module -> toModuleMetricsResource(metricValuesGroupedByModule.get(module))));
        return moduleTreeResource;
    }

    private MetricValuesSet toModuleMetricsResource(List<MetricValuePerModuleDTO> metricValuesPerModule) {
        MetricValuesSet moduleMetrics = new MetricValuesSet();
        for (MetricValuePerModuleDTO metricValuePerModuleDTO : metricValuesPerModule) {
            moduleMetrics.setMetricValue(metricValuePerModuleDTO.getMetric(), metricValuePerModuleDTO.getValue());
        }
        return moduleMetrics;
    }

}
