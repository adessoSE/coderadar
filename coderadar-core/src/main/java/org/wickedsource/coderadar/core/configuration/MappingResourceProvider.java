package org.wickedsource.coderadar.core.configuration;

import java.util.Set;

/**
 * Interface that allows a module to register an ORM mapping file that will be picked up at startup
 * of the Spring context and added to the configuration of the EntityManagerFactory.
 *
 * <p>Implementations of this interface have to registered in the Spring application context to be
 * picked up (i.e. they must have a @Component or similar annotation).
 */
public interface MappingResourceProvider {

  /**
   * Returns the paths within the classpath to the ORM mapping files that should be registered at
   * startup. Without leading "/".
   */
  Set<String> getMappingResource();
}
