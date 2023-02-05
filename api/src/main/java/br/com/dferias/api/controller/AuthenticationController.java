package br.com.dferias.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.LoginDTO;
import br.com.dferias.api.model.DTO.LoginFuncionarioDTO;
import br.com.dferias.api.model.DTO.TokenDTO;
import br.com.dferias.api.service.AuthenticationService;
import br.com.dferias.api.service.FuncionarioService;
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
  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private FuncionarioService funcionarioService;

  @PostMapping
  public ResponseEntity<LoginFuncionarioDTO> auth(
      @RequestBody @Validated LoginDTO loginDTO) {

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        loginDTO.getUser(),
        loginDTO.getPass());

    Authentication authentication = authenticationManager.authenticate(
        usernamePasswordAuthenticationToken);

    String token = tokenService.generateToken(authentication);
    Claims claims = Jwts.parser().setSigningKey(this.senhaSecreta).parseClaimsJws(token).getBody();
    Long id = Long.parseLong(claims.getSubject());

    token = "Bearer " + token;

    Funcionario funcionario = funcionarioService.getById(id);
    boolean isLider = funcionarioService.isLider(id);
    LoginFuncionarioDTO dados = new LoginFuncionarioDTO(token, isLider, funcionario);

    return ResponseEntity.ok(dados);
  }

  @GetMapping("/id")
  public Long getFuncionarioByToken(@RequestBody TokenDTO token) {
    return authenticationService.getIdByToken(token);
  }
}
