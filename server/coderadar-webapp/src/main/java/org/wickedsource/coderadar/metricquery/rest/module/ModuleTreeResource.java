package org.wickedsource.coderadar.metricquery.rest.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Tree structure that contains nested modules. For each module it contains a set of selected metric values.
 * Parent modules inherit the metric values of their sub modules.
 */
public class ModuleTreeResource extends ResourceSupport {

    private String name;

    @JsonUnwrapped
    private MetricValuesSet payload;

    private List<ModuleTreeResource> modules = new ArrayList<>();

    @JsonIgnore
    private ModuleTreeResource parent;

    public ModuleTreeResource() {
        this.name = "root";
        this.payload = new MetricValuesSet();
    }

    public ModuleTreeResource(String name, ModuleTreeResource parent, MetricValuesSet payload) {
        this.name = name;
        this.parent = parent;
        this.payload = payload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModuleTreeResource> getModules() {
        return modules;
    }

    public void setModules(List<ModuleTreeResource> modules) {
        this.modules = modules;
    }

    public ModuleTreeResource getParent() {
        return parent;
    }

    public void setParent(ModuleTreeResource parent) {
        this.parent = parent;
    }

    /**
     * Returns a list of all ancestors of the current node up to the root node.
     */
    @JsonIgnore
    public final List<ModuleTreeResource> getAncestors() {
        List<ModuleTreeResource> ancestors = new ArrayList<>();
        ModuleTreeResource parent = getParent();
        while (parent != null) {
            ancestors.add(parent);
            parent = parent.getParent();
        }
        return ancestors;
    }

    @JsonIgnore
    public final ModuleTreeResource getRoot() {
        ModuleTreeResource parent = getParent();
        while (parent != null) {
            if (parent.getParent() == null) {
                return parent;
            }
            parent = parent.getParent();
        }
        throw new IllegalStateException("Tree has no root!");
    }

    /**
     * Adds a list of modules which are transformed into a tree structure. Module names are considered to be a path
     * to a folder containing the source files of that module. A module is considered a sub module of
     * a parent module, if it is a subfolder of the parent module.
     *
     * @param modules         the list of modules to transform into a tree strucure.
     * @param payloadSupplier function that provides the payload for a node.
     */
    public void addModules(Collection<String> modules, PayloadSupplier<MetricValuesSet> payloadSupplier) {
        List<String> sortedModules = modules.stream()
                .sorted()
                .collect(Collectors.toList());

        ModuleTreeResource previousModule = null;
        for (String module : sortedModules) {
            ModuleTreeResource currentModule = null;
            if (previousModule == null) {
                currentModule = new ModuleTreeResource(module, this, payloadSupplier.getPayload(module));
                this.modules.add(currentModule);
            } else if (previousModule.getName() != null && module.startsWith(previousModule.getName())) {
                currentModule = new ModuleTreeResource(module, previousModule, payloadSupplier.getPayload(module));
                previousModule.getModules().add(currentModule);
            } else {
                List<ModuleTreeResource> ancestors = previousModule.getAncestors();
                for (ModuleTreeResource ancestor : ancestors) {
                    if (ancestor.getName() != null && module.startsWith(ancestor.getName())) {
                        currentModule = new ModuleTreeResource(module, ancestor, payloadSupplier.getPayload(module));
                        ancestor.getModules().add(currentModule);
                        break;
                    }
                }
                if (currentModule == null) {
                    currentModule = new ModuleTreeResource(module, this, payloadSupplier.getPayload(module));
                    this.modules.add(currentModule);
                }
            }

            previousModule = currentModule;
        }

        addMetricValuesFromChildren();
    }

    /**
     * Adds the values of this node's child modules to this nodes payload.
     */
    private void addMetricValuesFromChildren() {
        for (ModuleTreeResource childModule : modules) {
            for (Map.Entry<String, Long> entry : childModule.getPayload().getMetricValues().entrySet()) {
                Long currentValue = this.payload.getMetricValue(entry.getKey());
                if (currentValue == null) {
                    currentValue = 0L;
                }
                this.payload.setMetricValue(entry.getKey(), currentValue + entry.getValue());
            }
        }
    }

    public MetricValuesSet getPayload() {
        return payload;
    }

    public void setPayload(MetricValuesSet payload) {
        this.payload = payload;
    }
}
