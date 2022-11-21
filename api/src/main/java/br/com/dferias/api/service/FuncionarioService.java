package br.com.dferias.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dferias.api.DTO.FuncionarioDTO;
import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private TokenService tokenService;

    public FuncionarioDTO getFuncionarioDTO(String token) throws Exception {
        Long id = tokenService.getIdDoFuncionario(token);
        Funcionario funcionario = new Funcionario();
        try {

            funcionario = funcionarioRepository.findById(id).get();
        } catch (Exception e) {

        }

        return new FuncionarioDTO().convertToDTO(funcionario);

    }

}
