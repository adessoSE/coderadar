package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/** Base class for integration tests that need the full context of the Spring Boot application. */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class IntegrationTestTemplate {

  @Autowired private ProjectRepository projectRepository;
  @Autowired private UserRepository userRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @AfterEach
  void tearDown() {
    projectRepository.deleteAll();
    userRepository.deleteAll();
  }
}
