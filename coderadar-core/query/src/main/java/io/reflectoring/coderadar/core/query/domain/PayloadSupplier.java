package io.reflectoring.coderadar.core.query.domain;

@FunctionalInterface
public interface PayloadSupplier<T> {

    T getPayload(String module);
}
