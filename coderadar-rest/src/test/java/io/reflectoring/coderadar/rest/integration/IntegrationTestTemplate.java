package io.reflectoring.coderadar.rest.integration;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/** Base class for integration tests that need the full context of the Spring Boot application. */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class IntegrationTestTemplate {

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }
}
