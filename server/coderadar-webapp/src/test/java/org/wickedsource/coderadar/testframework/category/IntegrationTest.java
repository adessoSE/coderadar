package org.wickedsource.coderadar.testframework.category;

/**
 * Marker Interface used in JUnit @Category annotations to mark integration tests
 * that may take some time to finish and may have dependencies to other resources
 * like the internet and thus may fail if these resources are currently not available.
 */
public interface IntegrationTest {
}
