package br.com.dferias.api.service;

import br.com.dferias.api.model.DTO.FuncionarioDTO;
import br.com.dferias.api.model.Equipe;
import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.repository.EquipeRepository;
import br.com.dferias.api.repository.FuncionarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionarioService {

  @Autowired
  private FuncionarioRepository repository;

  @Autowired
  private PerfilService perfilService;

  @Autowired
  private EquipeRepository equipeRepository;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Transactional(rollbackFor = Exception.class)
  public Long saveDto(FuncionarioDTO funcionarioDTO) {
    funcionarioDTO.setPass(
        bCryptPasswordEncoder().encode(funcionarioDTO.getPass()));

    return cadastrar(new Funcionario(funcionarioDTO)).getId();
  }

  public Long getLiderId(Funcionario funcionario) {
    Equipe equipe = this.equipeRepository.findById(funcionario.getIdEquipe()).get();

    return equipe.getId_lider();
  }

  public Funcionario getById(Long id) {
    return this.repository.findById(id).get();
  }

  private Funcionario cadastrar(Funcionario funcionario) {
    Funcionario funcionarioCriado;
    funcionarioCriado = repository.save(funcionario);
    System.out.println("Funcionario criado" + funcionarioCriado);
    perfilService.vincularNovoPerfil(funcionarioCriado.getId());

    return funcionarioCriado;
  }

  public void createFuncionario(Funcionario funcionario) {
    cadastrar(funcionario);
  }

  public Optional<Funcionario> findById(Long id) {
    return repository.findById(id);
  }
}
