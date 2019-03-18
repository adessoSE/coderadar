package org.wickedsource.coderadar.metricquery.rest.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Tree structure that contains nested modules. For each module it contains a set of selected metric
 * values. Parent modules aggregate the metric values of their sub modules.
 */
public class MetricsTreeResource<P extends MetricsTreePayload> {

  private String name;

  private MetricsTreeNodeType type;

  @JsonUnwrapped private P payload;

  @JsonIgnore private Map<String, MetricsTreeResource<P>> childMap = new HashMap<>();

  private List<MetricsTreeResource<P>> children = new ArrayList<>();

  @JsonIgnore private MetricsTreeResource parent;

  public MetricsTreeResource() {}

  public MetricsTreeResource(P payload) {
    this.name = "root";
    this.type = MetricsTreeNodeType.MODULE;
    this.payload = payload;
  }

  public MetricsTreeResource(
      String name, MetricsTreeResource parent, P payload, MetricsTreeNodeType type) {
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

  @JsonProperty("children")
  public List<MetricsTreeResource<P>> getChildren() {
    return this.children;
  }

  public void setChildren(List<MetricsTreeResource<P>> children) {
    this.children = children;
    this.childMap.clear();
    for (MetricsTreeResource<P> child : children) {
      this.childMap.put(child.getName(), child);
    }
  }

  public void addChild(MetricsTreeResource<P> child) {
    this.children.add(child);
    this.childMap.put(child.getName(), child);
  }

  public MetricsTreeResource<P> getChild(String name) {
    return childMap.get(name);
  }

  public MetricsTreeResource getParent() {
    return parent;
  }

  public void setParent(MetricsTreeResource parent) {
    this.parent = parent;
  }

  /** Returns a list of all ancestors of the current node up to the root node. */
  @JsonIgnore
  public final List<MetricsTreeResource<P>> getAncestors() {
    List<MetricsTreeResource<P>> ancestors = new ArrayList<>();
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
   * Adds a list of modules which are transformed into a tree structure. Module names are considered
   * to be a path to a folder containing the source files of that module. A module is considered a
   * sub module of a parent module, if it is a sub folder of the parent module.
   *
   * @param modules the list of modules to transform into a tree strucure.
   * @param payloadSupplier function that provides the payload for a node.
   */
  public void addModules(
      Collection<String> modules,
      PayloadSupplier<P> payloadSupplier,
      NodeTypeSupplier nodeTypeSupplier) {
    List<String> sortedModules = modules.stream().sorted().collect(Collectors.toList());

    MetricsTreeResource<P> previousModule = null;
    for (String module : sortedModules) {
      MetricsTreeResource<P> currentModule = null;
      if (previousModule == null) {
        currentModule =
            new MetricsTreeResource<>(
                module,
                this,
                payloadSupplier.getPayload(module),
                nodeTypeSupplier.getNodeType(module));
        addChild(currentModule);
      } else if (previousModule.getName() != null && module.startsWith(previousModule.getName())) {
        currentModule =
            new MetricsTreeResource<>(
                module,
                previousModule,
                payloadSupplier.getPayload(module),
                nodeTypeSupplier.getNodeType(module));
        previousModule.getChildren().add(currentModule);
      } else {
        List<MetricsTreeResource<P>> ancestors = previousModule.getAncestors();
        for (MetricsTreeResource<P> ancestor : ancestors) {
          if (ancestor.getName() != null && module.startsWith(ancestor.getName())) {
            currentModule =
                new MetricsTreeResource<>(
                    module,
                    ancestor,
                    payloadSupplier.getPayload(module),
                    nodeTypeSupplier.getNodeType(module));
            ancestor.getChildren().add(currentModule);
            break;
          }
        }
        if (currentModule == null) {
          currentModule =
              new MetricsTreeResource<>(
                  module,
                  this,
                  payloadSupplier.getPayload(module),
                  nodeTypeSupplier.getNodeType(module));
          addChild(currentModule);
        }
      }

      previousModule = currentModule;
    }

    addMetricValuesFromChildren();
  }

  /** Adds the values of this node's child modules to this nodes payload. */
  private void addMetricValuesFromChildren() {
    for (MetricsTreeResource<P> childModule : childMap.values()) {
      this.payload.add(childModule.getPayload());
    }
  }

  public P getPayload() {
    return payload;
  }

  public void setPayload(P payload) {
    this.payload = payload;
  }

  public MetricsTreeNodeType getType() {
    return type;
  }

  public void setType(MetricsTreeNodeType type) {
    this.type = type;
  }
}
