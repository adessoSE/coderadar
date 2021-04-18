package io.reflectoring.coderadar.rest.authentication;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.domain.ProjectRole;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.domain.UserEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.rest.ControllerTestTemplate;
import io.reflectoring.coderadar.useradministration.port.driver.login.LoginUserCommand;
import io.reflectoring.coderadar.useradministration.service.UserUnauthenticatedException;
import io.reflectoring.coderadar.useradministration.service.UserUnauthorizedException;
import io.reflectoring.coderadar.useradministration.service.login.LoginUserService;
import io.reflectoring.coderadar.useradministration.service.permissions.SetUserRoleForProjectService;
import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

class AuthenticationServiceIntegrationTest extends ControllerTestTemplate {

  @Autowired private UserRepository userRepository;

  @Autowired private ProjectRepository projectRepository;

  @Autowired private AuthenticationService authenticationService;

  @Autowired private SetUserRoleForProjectService setUserRoleForProjectService;

  @Autowired private LoginUserService loginUserService;

  @Autowired private CoderadarConfigurationProperties coderadarConfigurationProperties;

  private ProjectEntity testProject;
  private UserEntity testUser;

  private Long projectId;

  @BeforeEach
  void setUp() {
    coderadarConfigurationProperties.setAuthentication(
        new CoderadarConfigurationProperties.Authentication().setEnabled(true));

    testProject = new ProjectEntity();
    testProject.setVcsUrl("https://valid.url");
    testProject.setName("project");
    testProject.setVcsStart(new Date());
    testProject.setVcsUsername("testUser");
    projectId = projectRepository.save(testProject).getId();

    testUser = new UserEntity();
    testUser.setUsername("username1");
    testUser.setPassword(PasswordUtil.hash("password1"));
    userRepository.save(testUser);
    loginUserService.login(new LoginUserCommand("username1", "password1"));
  }

  @AfterEach
  void tearDown() {
    coderadarConfigurationProperties.setAuthentication(
        new CoderadarConfigurationProperties.Authentication().setEnabled(false));
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Test
  void testUserIsAuthenticatedAsAdmin() {
    // Set up
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken("username1", "password1"));
    setUserRoleForProjectService.setRole(testProject.getId(), testUser.getId(), ProjectRole.ADMIN);

    // Test
    Assertions.assertDoesNotThrow(() -> authenticationService.authenticateAdmin(projectId));
    Assertions.assertDoesNotThrow(() -> authenticationService.authenticateMember(projectId));
  }

  @Test
  void testUserIsAuthenticatedAsMember() {
    // Set up
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken("username1", "password1"));
    setUserRoleForProjectService.setRole(testProject.getId(), testUser.getId(), ProjectRole.MEMBER);

    // Test
    Assertions.assertDoesNotThrow(() -> authenticationService.authenticateMember(projectId));
    Assertions.assertThrows(
        UserUnauthorizedException.class, () -> authenticationService.authenticateAdmin(projectId));
  }

  @Test
  void testThrowsExceptionWhenUserIsUnauthenticated() {
    Assertions.assertThrows(
        UserUnauthenticatedException.class,
        () -> authenticationService.authenticateAdmin(projectId));
    Assertions.assertThrows(
        UserUnauthenticatedException.class,
        () -> authenticationService.authenticateMember(projectId));
  }

  @Test
  void testThrowsExceptionWhenUserIsNotAdmin() {
    // Set up
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken("username1", "password1"));
    setUserRoleForProjectService.setRole(testProject.getId(), testUser.getId(), ProjectRole.MEMBER);

    // Test
    Assertions.assertThrows(
        UserUnauthorizedException.class, () -> authenticationService.authenticateAdmin(projectId));
    Assertions.assertDoesNotThrow(() -> authenticationService.authenticateMember(projectId));
  }

  @Test
  void testThrowsExceptionWhenUserHasNoRole() {
    // Set up
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken("username1", "password1"));

    // Test
    Assertions.assertThrows(
        UserUnauthorizedException.class, () -> authenticationService.authenticateAdmin(projectId));
    Assertions.assertThrows(
        UserUnauthorizedException.class, () -> authenticationService.authenticateMember(projectId));
  }
}
