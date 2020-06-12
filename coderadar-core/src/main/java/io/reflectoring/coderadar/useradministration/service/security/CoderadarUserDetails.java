package io.reflectoring.coderadar.useradministration.service.security;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Value
class CoderadarUserDetails implements UserDetails {

  private final String username;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;
  private final boolean enabled;
  private final boolean accountNonExpired;
  private final boolean accountNonLocked;
  private final boolean credentialsNonExpired;
}
