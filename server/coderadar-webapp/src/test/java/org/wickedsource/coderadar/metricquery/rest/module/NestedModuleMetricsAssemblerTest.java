package org.wickedsource.coderadar.metricquery.rest.module;

import org.junit.Test;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValuePerModuleDTO;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NestedModuleMetricsAssemblerTest {

    @Test
    public void modulesAreNestedCorrectly() {

        List<MetricValuePerModuleDTO> valuesPerModule = new ArrayList<>();
        valuesPerModule.add(new MetricValuePerModuleDTO("metric1", 10L, "server/foo/module1"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric2", 11L, "server/foo/module1"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric3", 12L, "server/foo/module1"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric1", 10L, "server/foo/module1/submodule1"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric2", 11L, "server/foo/module1/submodule1"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric3", 12L, "server/foo/module1/submodule1"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric1", 10L, "server/foo/module1/submodule2"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric2", 11L, "server/foo/module1/submodule2"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric3", 12L, "server/foo/module1/submodule2"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric1", 10L, "server/foo/module1/submodule1/subsubmodule3"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric2", 11L, "server/foo/module1/submodule1/subsubmodule3"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric3", 12L, "server/foo/module1/submodule1/subsubmodule3"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric1", 10L, "server/foo/bar/module4/submodule5"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric2", 11L, "server/foo/bar/module4/submodule5"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric3", 12L, "server/foo/bar/module4/submodule5"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric1", 10L, "server/foo/bar/module6/submodule7"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric2", 11L, "server/foo/bar/module6/submodule7"));
        valuesPerModule.add(new MetricValuePerModuleDTO("metric3", 12L, "server/foo/bar/module6/submodule7"));

        ModuleMetricsTreeResourceAssembler assembler = new ModuleMetricsTreeResourceAssembler();
        ModuleTreeResource resource = assembler.toResource(valuesPerModule);

        assertThat(resource.getModules()).hasSize(3);

    }

}