package io.reflectoring.coderadar.graph.useradministration;

import io.reflectoring.coderadar.domain.RefreshToken;
import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.useradministration.domain.RefreshTokenEntity;

public class RefreshTokenMapper implements Mapper<RefreshToken, RefreshTokenEntity> {
  @Override
  public RefreshToken mapGraphObject(RefreshTokenEntity nodeEntity) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setId(nodeEntity.getId());
    refreshToken.setToken(nodeEntity.getToken());
    return refreshToken;
  }

  @Override
  public RefreshTokenEntity mapDomainObject(RefreshToken domainObject) {
    RefreshTokenEntity refreshToken = new RefreshTokenEntity();
    refreshToken.setToken(domainObject.getToken());
    return refreshToken;
  }
}
