package org.wickedsource.coderadar.security.domain;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CoderadarUserDetails implements UserDetails {

	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean enabled;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;

	public CoderadarUserDetails(
			String username,
			String password,
			Collection<? extends GrantedAuthority> authorities,
			boolean enabled,
			boolean accountNonExpired,
			boolean accountNonLocked,
			boolean credentialsNonExpired) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
