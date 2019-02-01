package org.wickedsource.coderadar.testframework.template;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.wickedsource.coderadar.CoderadarTestApplication;

/** Base class for integration tests that need the full context of the Spring Boot application. */
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
@SpringBootTest(
  classes = CoderadarTestApplication.class,
  properties = {
    "coderadar.master=false",
    "coderadar.slave=false",
    "coderadar.workdir=build/coderadar-workdir",
    "coderadar.dateLocale=de_DE",
    // We want to use the same database instancein all tests, even when the default
    // is set to "true" with Spring Boot 1.5
    "spring.datasource.generate-unique-name=false",
    "spring.jpa.hibernate.ddl-auto=validate",
    "spring.jpa.hibernate.use-new-id-generator-mappings=true"
  }
)
@WebAppConfiguration
public abstract class IntegrationTestTemplate extends TestTemplate {

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }
}
