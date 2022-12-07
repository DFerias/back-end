package br.com.dferias.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.FuncionarioDTO;
import br.com.dferias.api.repository.FuncionarioRepository;

public class FuncionarioService {
    @Autowired
    FuncionarioRepository repository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long saveDto(FuncionarioDTO funcionarioDTO) {

        funcionarioDTO.setSenha(bCryptPasswordEncoder().encode(funcionarioDTO.getSenha()));

        return save(new Funcionario(funcionarioDTO)).getId();
    }

    private Funcionario save(Funcionario funcionario) {
        return repository.save(funcionario);
    }
}
