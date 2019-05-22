package io.reflectoring.coderadar.core.projectadministration.service.user.security;

import io.reflectoring.coderadar.core.projectadministration.CoderadarConfigurationProperties;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CoderadarSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;

  private TokenService tokenService;

  private CoderadarConfigurationProperties coderadarConfiguration;

  private CorsFilter corsFilter;

  @Autowired
  public CoderadarSecurityConfiguration(
      UserDetailsService userDetailsService,
      TokenService tokenService,
      CoderadarConfigurationProperties coderadarConfiguration,
      Optional<CorsFilter> corsFilter) {
    this.userDetailsService = userDetailsService;
    this.tokenService = tokenService;
    this.coderadarConfiguration = coderadarConfiguration;
    if (corsFilter.isPresent()) {
      this.corsFilter = corsFilter.get();
    }
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // user CoderadarUserDetailService for authentication
    auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    if (coderadarConfiguration.getAuthentication().getEnabled()) {
      http.sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .authorizeRequests()

          // only these endpoints can be called without authentication
          .antMatchers("/actuator", "/user/auth", "/user/registration", "/user/refresh")
          .permitAll()
          .anyRequest()
          .authenticated();

      // put JSON Web Token authentication before other ones
      http.addFilterBefore(
          new AuthenticationTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

      if (corsFilter != null) {
        http.addFilterBefore(corsFilter, AuthenticationTokenFilter.class);
      }
    }
  }
}
