package io.reflectoring.coderadar.useradministration.service.security;

import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.LoadUserPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CoderadarUserDetailService implements UserDetailsService {

  private final LoadUserPort loadUserPort;

  public CoderadarUserDetailService(LoadUserPort loadUserPort) {
    this.loadUserPort = loadUserPort;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = loadUserPort.loadUserByUsername(username);
    // TODO add authorities to user
    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("USER"));
    return new CoderadarUserDetails(
        user.getUsername(), user.getPassword(), authorities, true, true, true, true);
  }
}
