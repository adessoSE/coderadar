package io.reflectoring.coderadar.useradministration.service.security;

import java.util.Collection;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Value
class CoderadarUserDetails implements UserDetails {

  String username;
  String password;
  Collection<? extends GrantedAuthority> authorities;
  boolean enabled;
  boolean accountNonExpired;
  boolean accountNonLocked;
  boolean credentialsNonExpired;
}
