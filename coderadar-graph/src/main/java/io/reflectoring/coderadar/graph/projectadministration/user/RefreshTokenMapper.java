package io.reflectoring.coderadar.graph.projectadministration.user;

import io.reflectoring.coderadar.graph.AbstractMapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.RefreshTokenEntity;
import io.reflectoring.coderadar.projectadministration.domain.RefreshToken;

public class RefreshTokenMapper extends AbstractMapper<RefreshToken, RefreshTokenEntity> {
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
