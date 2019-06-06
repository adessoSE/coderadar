package io.reflectoring.coderadar.query.domain;

public interface MetricsTreePayload<P> {

  /**
   * Adds the content of the specified payload to this object.
   *
   * @param payload the payload to add.
   */
  void add(P payload);
}
