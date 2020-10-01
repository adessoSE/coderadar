package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.useradministration.repository.RefreshTokenRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteUserPort;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserAdapter implements DeleteUserPort {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  @SneakyThrows
  @Override
  public void deleteUser(long userId) {
    refreshTokenRepository.deleteByUser(userId);
    userRepository.deleteById(userId);
  }
}
