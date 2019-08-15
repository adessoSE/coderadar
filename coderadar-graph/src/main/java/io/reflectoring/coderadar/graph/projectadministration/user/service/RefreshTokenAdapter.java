package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.RefreshTokenEntity;
import io.reflectoring.coderadar.graph.projectadministration.user.RefreshTokenMapper;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.projectadministration.user.repository.UserRepository;
import io.reflectoring.coderadar.projectadministration.RefreshTokenNotFoundException;
import io.reflectoring.coderadar.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.RefreshToken;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RefreshTokenPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
      return refreshTokenMapper.mapNodeEntity(refreshTokenEntity);
    } else {
      throw new RefreshTokenNotFoundException();
    }
  }

  @Override
  @Transactional
  public void deleteByUser(User user) {
    refreshTokenRepository.deleteByUser(
        userRepository
            .findById(user.getId())
            .orElseThrow(() -> new UserNotFoundException(user.getId())));
  }

  @Override
  public void updateRefreshToken(String oldToken, String newToken) {
    RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(oldToken);
    refreshToken.setToken(newToken);
    refreshTokenRepository.save(refreshToken);
  }

  @Override
  public void saveToken(RefreshToken refreshToken) {
    refreshTokenRepository.save(refreshTokenMapper.mapDomainObject(refreshToken));
  }
}
