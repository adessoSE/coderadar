package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.useradministration.RefreshTokenMapper;
import io.reflectoring.coderadar.graph.useradministration.domain.RefreshTokenEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.useradministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.RefreshToken;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.RefreshTokenPort;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenAdapter implements RefreshTokenPort {

  private final RefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenMapper refreshTokenMapper = new RefreshTokenMapper();

  public RefreshTokenAdapter(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }

  @Override
  public RefreshToken findByToken(String refreshToken) {
    RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken);
    if (refreshTokenEntity != null) {
      refreshTokenEntity.setUser(refreshTokenRepository.findUserByToken(refreshToken));
      return refreshTokenMapper.mapNodeEntity(refreshTokenEntity);
    } else {
      throw new RefreshTokenNotFoundException();
    }
  }

  @Override
  public void deleteByUser(User user) {
    refreshTokenRepository.deleteByUser((user.getId()));
  }

  @Override
  public void saveToken(RefreshToken refreshToken) {
    refreshTokenRepository.save(refreshTokenMapper.mapDomainObject(refreshToken));
  }
}
