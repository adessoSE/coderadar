package org.wickedsource.coderadar.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import org.wickedsource.coderadar.security.service.TokenService;

/**
 * Filter for authentication of requests with JSON Web Tokens. This filter reads the access token
 * from http-request header and validates the signature of the token. If the signature is valid, a
 * {@link UsernamePasswordAuthenticationToken} will be set as {@link
 * org.springframework.security.core.Authentication} in {@link
 * org.springframework.security.core.context.SecurityContext}. That means other filters of the
 * filter chain will not authenticate the request anymore. If the signature of the access token is
 * not valid, other filters will try to authenticate the request. Note: the filter must be added or
 * inserted to the filter chain in the security configuration.
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

  static final String TOKEN_HEADER = "Authorization";

  private final TokenService tokenService;

  private final AccessDeniedHandler accessDeniedHandler;

  @Autowired
  public AuthenticationTokenFilter(
      TokenService tokenService, AccessDeniedHandler accessDeniedHandler) {
    this.tokenService = tokenService;
    this.accessDeniedHandler = accessDeniedHandler;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = request.getHeader(TOKEN_HEADER);
    if (token != null) {
      try {
        DecodedJWT decodedToken = tokenService.verify(token);
        JWT jwt = JWT.decode(decodedToken.getToken());
        Claim username = jwt.getClaim("username");
        // so long we don't have authorisation, we just set GrantedAuthority to null
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(username.asString(), null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      } catch (JWTVerificationException e) {
        // If the access token is sent and could not be verified, no further filter in the chain will be used. The user gets 403 Error.
        // If you let other filter try to authenticate the user, then OAuth2 filter tries to authenticate the user und redirects him or her to authorization server.
        // This behavior is not expected with the JWT access token, that's why the AccessDeniedException is thrown here.
        accessDeniedHandler.handle(
            request,
            response,
            new AccessDeniedException("Authentication error. Token is not valid", e));
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}
