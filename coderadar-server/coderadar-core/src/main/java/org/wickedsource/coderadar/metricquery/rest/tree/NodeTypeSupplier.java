package org.wickedsource.coderadar.metricquery.rest.tree;

@FunctionalInterface
public interface NodeTypeSupplier {

	MetricsTreeNodeType getNodeType(String module);
}
