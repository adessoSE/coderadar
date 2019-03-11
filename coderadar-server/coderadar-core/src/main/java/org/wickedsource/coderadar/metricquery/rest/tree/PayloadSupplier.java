package org.wickedsource.coderadar.metricquery.rest.tree;

@FunctionalInterface
public interface PayloadSupplier<T> {

  T getPayload(String module);
}
