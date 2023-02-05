package br.com.dferias.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.TokenDTO;
import br.com.dferias.api.repository.FuncionarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class AuthenticationService implements UserDetailsService {

  @Autowired
  private FuncionarioRepository repository;

  @Value("${jwt.secret}")
  private String senhaSecreta;

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    Optional<Funcionario> optional = repository.findByEmail(username);

    if (optional.isPresent()) {
      return optional.get();
    }

    throw new UsernameNotFoundException("User not found");
  }

  public Long getIdByToken(TokenDTO token) {
    try {
      Claims claims = Jwts.parser().setSigningKey(this.senhaSecreta).parseClaimsJws(token.getToken()).getBody();

      return Long.parseLong(claims.getSubject());
    } catch (Exception e) {

      return Long.parseLong("-1");
    }

  }
}
