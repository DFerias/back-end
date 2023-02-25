package br.com.dferias.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dferias.api.model.Equipe;
import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.FuncionarioDTO;
import br.com.dferias.api.repository.EquipeRepository;
import br.com.dferias.api.repository.FuncionarioRepository;
import br.com.dferias.api.util.Validador;

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

    perfilService.vincularNovoPerfil(funcionarioCriado.getId());

    return funcionarioCriado;
  }

  public void createFuncionario(Funcionario funcionario) {
    cadastrar(funcionario);
  }

  public Optional<Funcionario> findById(Long id) {
    return repository.findById(id);
  }

  public boolean isLider(Long id) {
    List<Equipe> equipes = equipeRepository.findAll();

    for (Equipe equipe : equipes) {
      if (equipe.getId_lider().equals(id)) {
        return true;
      }
    }
    return false;

  }

  public void diminuirSaldo(Ferias ferias) {
    Integer quantidade = new Validador().getDiferencaEntreDatas(ferias.getInicio(), ferias.getFim());

    repository.diminuirSaldoFerias(quantidade, ferias.getIdFuncionario());
  }
}
