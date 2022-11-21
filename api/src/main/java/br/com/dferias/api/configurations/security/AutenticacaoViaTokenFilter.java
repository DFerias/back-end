package br.com.dferias.api.configurations.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.dferias.api.model.Login;
import br.com.dferias.api.repository.FuncionarioRepository;
import br.com.dferias.api.service.TokenService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService servicoToken;
    private FuncionarioRepository funcionarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);
        boolean tokenValido = servicoToken.isTokenValido(token);

        if (tokenValido) {

            autenticarUsuario(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(String token) {
        System.out.println("Autenticar usuario");
        try {
            Long id = servicoToken.getIdDoFuncionario(token);
            Login login = new Login(funcionarioRepository.findById(id).get());
            UsernamePasswordAuthenticationToken autorizacao = new UsernamePasswordAuthenticationToken(login, null,
                    login.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autorizacao);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String getToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {

            return null;
        }

        return token.substring(7, token.length());
    }

}
