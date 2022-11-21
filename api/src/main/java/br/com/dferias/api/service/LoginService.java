package br.com.dferias.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.dferias.api.DTO.FuncionarioDTO;
import br.com.dferias.api.DTO.LoginDTO;
import br.com.dferias.api.DTO.TokenDTO;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager autenticacao;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FuncionarioService funcionarioService;

    public TokenDTO enviarToken(LoginDTO login) {
        System.out.println("logando");
        try {
            UsernamePasswordAuthenticationToken dadosLogin = login.converter();

            System.out.println("Convertido");
            // Authentication autorizacao = autenticacao.authenticate(dadosLogin);
            Authentication auth = null;
            try {
                auth = autenticacao.authenticate(dadosLogin);
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);
                System.out.println("setou");
            } catch (Exception e) {
                // TODO: handle exception
            }

            System.out.println("Autenticate");
            String token = tokenService.gerarToken(auth);

            FuncionarioDTO funcionarioDTO = funcionarioService.getFuncionarioDTO(token);

            return new TokenDTO(token, "Bearer", funcionarioDTO);
        } catch (Exception e) {

            return null;
        }
    }

}
