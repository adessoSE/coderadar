package io.reflectoring.coderadar.useradministration.service.security;

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
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

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

  private static final String TOKEN_HEADER = "Authorization";

  private final TokenService tokenService;

  @Autowired
  AuthenticationTokenFilter(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
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
        logger.error("Authentication error. Token is not valid: ", e);
      } catch (NullPointerException e) {
        if (e.getMessage() != null && e.getMessage().startsWith("The Token has expired on")) {
          logger.error("Authentication error. Token is expired.");
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
