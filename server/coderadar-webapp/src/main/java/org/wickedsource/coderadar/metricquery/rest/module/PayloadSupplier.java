package org.wickedsource.coderadar.metricquery.rest.module;

@FunctionalInterface
public interface PayloadSupplier<T> {

    T getPayload(String module);

}
