package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RefreshTokenPort;
import org.springframework.stereotype.Service;

@Service("RefreshTokenServiceNeo4j")
public class RefreshTokenService implements RefreshTokenPort {
  @Override
  public String createAccessToken(String refreshToken) {
    return "1";
  }
}
