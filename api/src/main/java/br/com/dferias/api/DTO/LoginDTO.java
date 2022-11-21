package br.com.dferias.api.DTO;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;

@Getter
public class LoginDTO {

    private String email;
    private String senha;

    public UsernamePasswordAuthenticationToken converter() {

        return new UsernamePasswordAuthenticationToken(email, senha);
    }

}
