package io.reflectoring.coderadar.rest.integration;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/** Base class for integration tests that need the full context of the Spring Boot application. */
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
public abstract class IntegrationTestTemplate {

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }
}
