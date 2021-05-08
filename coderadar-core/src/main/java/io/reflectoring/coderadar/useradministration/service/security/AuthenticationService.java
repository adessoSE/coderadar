package io.reflectoring.coderadar.useradministration.service.security;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserRoleForProjectPort;
import io.reflectoring.coderadar.useradministration.service.UserUnauthenticatedException;
import io.reflectoring.coderadar.useradministration.service.UserUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final GetUserRoleForProjectPort getUserRoleForProjectPort;
  private final GetUserPort getUserPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  public void authenticateMember(long projectId) {
    if (coderadarConfigurationProperties.getAuthentication().isEnabled()
        && getUserRole(projectId) == null) {
      throw new UserUnauthorizedException();
    }
  }

  public void authenticatePlatformAdmin() {
    if (coderadarConfigurationProperties.getAuthentication().isEnabled()) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (!getUserPort
          .getUserByUsername(((String) authentication.getPrincipal()))
          .isPlatformAdmin()) {
        throw new UserUnauthenticatedException();
      }
    }
  }

  public void authenticateAdmin(long projectId) {
    if (coderadarConfigurationProperties.getAuthentication().isEnabled()
        && getUserRole(projectId) != ProjectRole.ADMIN) {
      throw new UserUnauthorizedException();
    }
  }

  private ProjectRole getUserRole(long projectId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof String) {
      return getUserRoleForProjectPort.getRole(
          projectId,
          getUserPort.getUserByUsername(((String) authentication.getPrincipal())).getId());
    } else {
      throw new UserUnauthenticatedException();
    }
  }
}
