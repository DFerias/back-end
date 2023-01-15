package br.com.dferias.api.service;

import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.repository.FeriasRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeriasService {

  @Autowired
  private FeriasRepository feriasRepository;

  @Autowired
  private FuncionarioService funcionarioService;

  public Ferias save(Ferias ferias) {
    ferias.setStatus("PENDENTE");
    Funcionario funcionario = funcionarioService.getById(
      ferias.getIdFuncionario()
    );
    ferias.setIdLider(funcionarioService.getLiderId(funcionario));
    return feriasRepository.save(ferias);
  }

  public List<Ferias> findAll() {
    return feriasRepository.findAll();
  }
}
