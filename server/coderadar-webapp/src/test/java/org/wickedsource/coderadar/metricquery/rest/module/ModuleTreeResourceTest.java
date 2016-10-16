package org.wickedsource.coderadar.metricquery.rest.module;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ModuleTreeResourceTest {

    @Test
    public void addedModulesAreNestedCorrectly() {
        ModuleTreeResource tree = new ModuleTreeResource(null, null, null);
        tree.addModules(Arrays.asList("server/foo/module1",
                "server/foo/module1/submodule1",
                "server/foo/module1/submodule2",
                "server/foo/module1/submodule1/subsubmodule3",
                "server/foo/bar/module4/submodule5",
                "server/foo/bar/module6/submodule7"), (module -> new MetricValuesSet()));

        assertThat(tree.getModules()).hasSize(3);
        assertThat(tree.getModules().get(0).getName()).isEqualTo("server/foo/bar/module4/submodule5");
        assertThat(tree.getModules().get(0).getModules()).hasSize(0);
        assertThat(tree.getModules().get(1).getName()).isEqualTo("server/foo/bar/module6/submodule7");
        assertThat(tree.getModules().get(1).getModules()).hasSize(0);
        assertThat(tree.getModules().get(2).getName()).isEqualTo("server/foo/module1");
        assertThat(tree.getModules().get(2).getModules()).hasSize(2);
        assertThat(tree.getModules().get(2).getModules().get(0).getName()).isEqualTo("server/foo/module1/submodule1");
        assertThat(tree.getModules().get(2).getModules().get(1).getName()).isEqualTo("server/foo/module1/submodule2");
    }

}