package io.reflectoring.coderadar.core.query.domain;

@FunctionalInterface
public interface NodeTypeSupplier {

    MetricsTreeNodeType getNodeType(String module);
}
