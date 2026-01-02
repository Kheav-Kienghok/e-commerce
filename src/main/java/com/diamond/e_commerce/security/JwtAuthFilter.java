package com.diamond.e_commerce.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final CustomUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    final String requestUri = request.getRequestURI();
    final String method = request.getMethod();

    log.debug("JWT filter started → {} {}", method, requestUri);

    try {
      String token = getTokenFromRequest(request);

      if (token == null) {
        log.trace("No Authorization header found for {} {}", method, requestUri);
      } else {
        log.trace("Authorization header detected for {} {}", method, requestUri);

        jwtUtils.validateTokenSafe(token).ifPresentOrElse(decodedJWT -> {
          try {
            String username = decodedJWT.getSubject();
            log.debug("JWT validated successfully for user='{}'", username);

            var userDetails = userDetailsService.loadUserByUsername(username);

            var authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

            authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("SecurityContext populated for user='{}'", username);

          } catch (JWTVerificationException | IllegalArgumentException ex) {
            log.warn("JWT validation failed: {}", ex.getMessage());
          }
        }, () -> {
          log.warn("Invalid or expired JWT for {} {}", method, requestUri);
        });
      }

    } catch (Exception ex) {
      // Catch-all to prevent filter chain break
      log.error("Unexpected error in JwtAuthFilter for {} {}",
          method, requestUri, ex);
    }

    filterChain.doFilter(request, response);

    log.debug("JWT filter completed → {} {}", method, requestUri);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
