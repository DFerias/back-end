package br.com.dferias.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.FuncionarioDTO;
import br.com.dferias.api.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
    @Autowired
    FuncionarioRepository repository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long saveDto(FuncionarioDTO funcionarioDTO) {

        funcionarioDTO.setPass(bCryptPasswordEncoder().encode(funcionarioDTO.getPass()));

        return save(new Funcionario(funcionarioDTO)).getId();
    }

    private Funcionario save(Funcionario funcionario) {
        return repository.save(funcionario);
    }

    public void createFuncionario(Funcionario funcionario) {
        System.out.println(funcionario);
        repository.save(funcionario);
    }

    public Optional<Funcionario> findById(Long id) {
        return repository.findById(id);
    }
}
