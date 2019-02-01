package org.wickedsource.coderadar.metricquery.rest.tree;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedByFileMetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedByModuleMetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO;

public class MetricsTreeResourceAssembler
		extends ResourceAssemblerSupport<List<GroupedMetricValueDTO>, MetricsTreeResource> {

	public MetricsTreeResourceAssembler() {
		super(MetricsTreeController.class, MetricsTreeResource.class);
	}

	@Override
	public MetricsTreeResource<CommitMetricsPayload> toResource(
			List<GroupedMetricValueDTO> metricValuesPerModule) {
		Map<String, List<GroupedMetricValueDTO>> metricValuesGroupedByModule =
				metricValuesPerModule
						.stream()
						.collect(Collectors.groupingBy(GroupedMetricValueDTO::getGroup));
		MetricsTreeResource<CommitMetricsPayload> metricsTreeResource =
				new MetricsTreeResource<>(new CommitMetricsPayload());
		metricsTreeResource.addModules(
				metricValuesGroupedByModule.keySet(),
				(module -> providePayload(metricValuesGroupedByModule.get(module))),
				(module -> provideNodeType(module, metricValuesGroupedByModule)));
		return metricsTreeResource;
	}

	private MetricsTreeNodeType provideNodeType(
			String module, Map<String, List<GroupedMetricValueDTO>> metricValuesGroupedByModule) {
		List<GroupedMetricValueDTO> metricsList = metricValuesGroupedByModule.get(module);
		if (metricsList == null || metricsList.isEmpty()) {
			return null;
		}
		GroupedMetricValueDTO firstValue = metricsList.get(0);
		if (firstValue instanceof GroupedByModuleMetricValueDTO) {
			return MetricsTreeNodeType.MODULE;
		} else if (firstValue instanceof GroupedByFileMetricValueDTO) {
			return MetricsTreeNodeType.FILE;
		}
		return null;
	}

	private CommitMetricsPayload providePayload(List<GroupedMetricValueDTO> metricValuesPerModule) {
		MetricValuesSet moduleMetrics = new MetricValuesSet();
		for (GroupedMetricValueDTO groupedMetricValueDTO : metricValuesPerModule) {
			moduleMetrics.setMetricValue(
					groupedMetricValueDTO.getMetric(), groupedMetricValueDTO.getValue());
		}
		return new CommitMetricsPayload(moduleMetrics);
	}
}
