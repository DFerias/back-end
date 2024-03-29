package br.com.dferias.api.config;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.service.TokenService;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String tokenFromHeader = getTokenFromHeader(request);
    boolean tokenValid = true;
    try {
      tokenValid = tokenService.isTokenValid(tokenFromHeader);
    } catch (Exception e) {}
    if (tokenValid) {
      this.authenticate(tokenFromHeader);
    }

    filterChain.doFilter(request, response);
  }

  private void authenticate(String tokenFromHeader) {
    Long id = Integer.toUnsignedLong(tokenService.getTokenId(tokenFromHeader));

    Optional<Funcionario> optionalUser = tokenService.findById(id);

    if (optionalUser.isPresent()) {
      Funcionario user = optionalUser.get();

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        user,
        null,
        user.getPerfis()
      );
      SecurityContextHolder
        .getContext()
        .setAuthentication(usernamePasswordAuthenticationToken);
    }
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }
    token = token.substring(7, token.length());
    return token;
  }
}
