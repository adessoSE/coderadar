package io.reflectoring.coderadar.graph.projectadministration.user.service;

import io.reflectoring.coderadar.graph.projectadministration.user.repository.LoadUserRepository;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadUserAdapter implements LoadUserPort {

  private final LoadUserRepository loadUserRepository;

  @Autowired
  public LoadUserAdapter(LoadUserRepository loadUserRepository) {
    this.loadUserRepository = loadUserRepository;
  }

  @Override
  public Optional<User> loadUser(Long id) {
    return loadUserRepository.findById(id);
  }

  @Override
  public Optional<User> loadUserByUsername(String username) {
    return loadUserRepository.findByUsername(username);
  }
}
