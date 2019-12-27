package io.reflectoring.coderadar.graph.useradministration;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.useradministration.domain.RefreshTokenEntity;
import io.reflectoring.coderadar.useradministration.domain.RefreshToken;

public class RefreshTokenMapper implements Mapper<RefreshToken, RefreshTokenEntity> {
  @Override
  public RefreshToken mapNodeEntity(RefreshTokenEntity nodeEntity) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setId(nodeEntity.getId());
    refreshToken.setToken(nodeEntity.getToken());
    refreshToken.setUser(new UserMapper().mapNodeEntity(nodeEntity.getUser()));
    return refreshToken;
  }

  @Override
  public RefreshTokenEntity mapDomainObject(RefreshToken domainObject) {
    RefreshTokenEntity refreshToken = new RefreshTokenEntity();
    refreshToken.setId(domainObject.getId());
    refreshToken.setToken(domainObject.getToken());
    refreshToken.setUser(new UserMapper().mapDomainObject(domainObject.getUser()));
    return refreshToken;
  }
}
