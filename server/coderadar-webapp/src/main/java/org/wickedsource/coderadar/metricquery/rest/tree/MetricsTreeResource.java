package org.wickedsource.coderadar.metricquery.rest.tree;

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
public class MetricsTreeResource extends ResourceSupport {

    private String name;

    private MetricsTreeNodeType type;

    @JsonUnwrapped
    private MetricValuesSet payload;

    private List<MetricsTreeResource> children = new ArrayList<>();

    @JsonIgnore
    private MetricsTreeResource parent;

    public MetricsTreeResource() {
        this.name = "root";
        this.payload = new MetricValuesSet();
        this.type = MetricsTreeNodeType.MODULE;
    }

    public MetricsTreeResource(String name, MetricsTreeResource parent, MetricValuesSet payload, MetricsTreeNodeType type) {
        this.name = name;
        this.parent = parent;
        this.payload = payload;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MetricsTreeResource> getChildren() {
        return children;
    }

    public void setChildren(List<MetricsTreeResource> children) {
        this.children = children;
    }

    public MetricsTreeResource getParent() {
        return parent;
    }

    public void setParent(MetricsTreeResource parent) {
        this.parent = parent;
    }

    /**
     * Returns a list of all ancestors of the current node up to the root node.
     */
    @JsonIgnore
    public final List<MetricsTreeResource> getAncestors() {
        List<MetricsTreeResource> ancestors = new ArrayList<>();
        MetricsTreeResource parent = getParent();
        while (parent != null) {
            ancestors.add(parent);
            parent = parent.getParent();
        }
        return ancestors;
    }

    @JsonIgnore
    public final MetricsTreeResource getRoot() {
        MetricsTreeResource parent = getParent();
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
    public void addModules(Collection<String> modules, PayloadSupplier<MetricValuesSet> payloadSupplier, NodeTypeSupplier nodeTypeSupplier) {
        List<String> sortedModules = modules.stream()
                .sorted()
                .collect(Collectors.toList());

        MetricsTreeResource previousModule = null;
        for (String module : sortedModules) {
            MetricsTreeResource currentModule = null;
            if (previousModule == null) {
                currentModule = new MetricsTreeResource(module, this, payloadSupplier.getPayload(module), nodeTypeSupplier.getNodeType(module));
                this.children.add(currentModule);
            } else if (previousModule.getName() != null && module.startsWith(previousModule.getName())) {
                currentModule = new MetricsTreeResource(module, previousModule, payloadSupplier.getPayload(module), nodeTypeSupplier.getNodeType(module));
                previousModule.getChildren().add(currentModule);
            } else {
                List<MetricsTreeResource> ancestors = previousModule.getAncestors();
                for (MetricsTreeResource ancestor : ancestors) {
                    if (ancestor.getName() != null && module.startsWith(ancestor.getName())) {
                        currentModule = new MetricsTreeResource(module, ancestor, payloadSupplier.getPayload(module), nodeTypeSupplier.getNodeType(module));
                        ancestor.getChildren().add(currentModule);
                        break;
                    }
                }
                if (currentModule == null) {
                    currentModule = new MetricsTreeResource(module, this, payloadSupplier.getPayload(module), nodeTypeSupplier.getNodeType(module));
                    this.children.add(currentModule);
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
        for (MetricsTreeResource childModule : children) {
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

    public MetricsTreeNodeType getType() {
        return type;
    }

    public void setType(MetricsTreeNodeType type) {
        this.type = type;
    }
}
