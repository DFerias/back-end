package br.com.dferias.api.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.dferias.api.model.Funcionario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Configurable
@Slf4j
public class TokenService {

  @Autowired
  private FuncionarioService funcionarioService;

  @Value("${jwt.expiration}")
  private String expiration;

  @Value("${jwt.secret}")
  private String secret = "rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!m'D&]{@Vr?G;2?XhbC:Qa#9#eMLN\\}x3?JR3.2zr~v)gYF^8\\:8>:XfB:Ww75N/emt9Yj[bQMNCWwW\\J?N,nvH.<2\\.r~w]*e~vgak)X\"v8H`MH/7\"2E`,^k @n<vE-wD3g9JWPy;CrY*.Kd2_D])=><D?YhBaSua5hW%{2]_FVXzb9`8FH^b[X3jzVER&:jw2<=c38=>L/zBq`}C6tT*cCSVC^c]-L}&/";

  public String generateToken(Authentication authentication) {
    Funcionario usuario = (Funcionario) authentication.getPrincipal();

    Date now = new Date();
    Date exp = new Date(now.getTime() + Long.parseLong(expiration));

    return Jwts
        .builder()
        .setIssuer("dferias")
        .setSubject(usuario.getId().toString())
        .setIssuedAt(new Date())
        .setExpiration(exp)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (Exception e) {

      return false;
    }
  }

  public Integer getTokenId(String token) {
    Claims body = Jwts
        .parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
    return Integer.valueOf(body.getSubject());
  }

  public Optional<Funcionario> findById(Long id) {
    return funcionarioService.findById(id);
  }
}
