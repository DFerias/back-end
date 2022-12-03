package br.com.dferias.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.repository.FuncionarioRepository;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private FuncionarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Funcionario> optional = repository.findByEmail(username);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new UsernameNotFoundException("User not found");
    }

}
