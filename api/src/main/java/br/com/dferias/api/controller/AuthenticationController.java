package br.com.dferias.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dferias.api.model.DTO.LoginDTO;
import br.com.dferias.api.model.DTO.TokenDTO;
import br.com.dferias.api.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  @Value("${jwt.secret}")
  private String senhaSecreta;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  public ResponseEntity<TokenDTO> auth(
      @RequestBody @Validated LoginDTO loginDTO) {
    System.out.println("Logando");
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        loginDTO.getUser(),
        loginDTO.getPass());

    Authentication authentication = authenticationManager.authenticate(
        usernamePasswordAuthenticationToken);

    String token = tokenService.generateToken(authentication);

    return ResponseEntity.ok(new TokenDTO("Bearer", token));
  }

  @GetMapping("/id")
  public Long getFuncionarioId(@RequestBody TokenDTO token) throws Exception {

    try {
      Claims claims = Jwts.parser().setSigningKey(this.senhaSecreta).parseClaimsJws(token.getToken()).getBody();
      System.out.println(Long.parseLong(claims.getSubject()));
      return Long.parseLong(claims.getSubject());
    } catch (Exception e) {

      return Long.parseLong("-1");
    }

  }
}
