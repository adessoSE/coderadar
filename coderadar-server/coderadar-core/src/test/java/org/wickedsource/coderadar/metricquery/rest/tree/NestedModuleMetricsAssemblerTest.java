package org.wickedsource.coderadar.metricquery.rest.tree;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedByModuleMetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.GroupedMetricValueDTO;

public class NestedModuleMetricsAssemblerTest {

  @Test
  public void modulesAreNestedCorrectly() {

    List<GroupedMetricValueDTO> valuesPerModule = new ArrayList<>();
    valuesPerModule.add(new GroupedByModuleMetricValueDTO("metric1", 10L, "server/foo/module1"));
    valuesPerModule.add(new GroupedByModuleMetricValueDTO("metric2", 11L, "server/foo/module1"));
    valuesPerModule.add(new GroupedByModuleMetricValueDTO("metric3", 12L, "server/foo/module1"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric1", 10L, "server/foo/module1/submodule1"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric2", 11L, "server/foo/module1/submodule1"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric3", 12L, "server/foo/module1/submodule1"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric1", 10L, "server/foo/module1/submodule2"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric2", 11L, "server/foo/module1/submodule2"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric3", 12L, "server/foo/module1/submodule2"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO(
            "metric1", 10L, "server/foo/module1/submodule1/subsubmodule3"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO(
            "metric2", 11L, "server/foo/module1/submodule1/subsubmodule3"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO(
            "metric3", 12L, "server/foo/module1/submodule1/subsubmodule3"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric1", 10L, "server/foo/bar/module4/submodule5"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric2", 11L, "server/foo/bar/module4/submodule5"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric3", 12L, "server/foo/bar/module4/submodule5"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric1", 10L, "server/foo/bar/module6/submodule7"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric2", 11L, "server/foo/bar/module6/submodule7"));
    valuesPerModule.add(
        new GroupedByModuleMetricValueDTO("metric3", 12L, "server/foo/bar/module6/submodule7"));

    MetricsTreeResourceAssembler assembler = new MetricsTreeResourceAssembler();
    MetricsTreeResource resource = assembler.toResource(valuesPerModule);

    assertThat(resource.getChildren()).hasSize(3);
  }
}
