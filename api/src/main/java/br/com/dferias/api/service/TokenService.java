package br.com.dferias.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.dferias.api.model.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiracao;
    @Value("${forum.jwt.secret}")
    private String secretKey;

    public String gerarToken(Authentication autorizacao) {

        Login logado = (Login) autorizacao.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiracao));

        return Jwts.builder()
                .setIssuer("API de login")
                .setSubject(String.valueOf(logado.getId()))
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isTokenValido(String token) {

        try {
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);

            return true;
        } catch (Exception e) {

            return false;
        }

    }

    public Long getIdDoFuncionario(String token) throws Exception {
        System.out.println("get id do funcionario");
        try {
            Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();

            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {

            throw new Exception("Token inválido");
        }
    }

}
