package io.reflectoring.coderadar.graph.useradministration.service;

import io.reflectoring.coderadar.graph.useradministration.RefreshTokenMapper;
import io.reflectoring.coderadar.graph.useradministration.domain.RefreshTokenEntity;
import io.reflectoring.coderadar.graph.useradministration.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.RefreshToken;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.RefreshTokenPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenAdapter implements RefreshTokenPort {

  private final RefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenMapper refreshTokenMapper = new RefreshTokenMapper();
  private final UserRepository userRepository;

  @Autowired
  public RefreshTokenAdapter(
      RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
  }

  @Override
  public RefreshToken findByToken(String refreshToken) throws RefreshTokenNotFoundException {
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
