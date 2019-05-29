package io.reflectoring.coderadar.core.projectadministration.service.user.security;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.service.CoderadarUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CoderadarUserDetailService implements UserDetailsService {

  private final LoadUserPort loadUserPort;

  @Autowired
  public CoderadarUserDetailService(LoadUserPort loadUserPort) {
    this.loadUserPort = loadUserPort;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = loadUserPort.loadUserByUsername(username);
    if (!user.isPresent()) {
      throw new UsernameNotFoundException(username);
    }
    // TODO add authorities to user
    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("USER"));
    return new CoderadarUserDetails(
        user.get().getUsername(), user.get().getPassword(), authorities, true, true, true, true);
  }
}
