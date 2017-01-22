package org.wickedsource.coderadar.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.wickedsource.coderadar.security.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for authentication of requests with tokens
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    public final static String TOKEN_HEADER = "Authorization";

    private final TokenService tokenService;

    @Autowired
    public AuthenticationTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null) {
            try {
                DecodedJWT decodedToken = tokenService.verify(token);
                JWT jwt = JWT.decode(decodedToken.getToken());
                Claim username = jwt.getClaim("username");
                // so long we don't have authorisation, we just set GrantedAuthority to null
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username.asString(), null, null);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (JWTVerificationException e) {
                // in case of verification error a further filter will take care about authentication error
                logger.error("Authentication error. Token is not valid", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
