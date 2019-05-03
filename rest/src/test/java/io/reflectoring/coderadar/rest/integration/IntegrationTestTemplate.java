package io.reflectoring.coderadar.rest.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/** Base class for integration tests that need the full context of the Spring Boot application. */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CoderadarTestApplication.class)
@WebAppConfiguration
public abstract class IntegrationTestTemplate {

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }
}
