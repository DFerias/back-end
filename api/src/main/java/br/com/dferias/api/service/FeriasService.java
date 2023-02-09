package br.com.dferias.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.NotAcceptableStatusException;

import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.repository.FeriasRepository;
import br.com.dferias.api.util.Utilitario;
import br.com.dferias.api.util.Validador;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FeriasService {

  @Autowired
  private FeriasRepository feriasRepository;

  @Autowired
  private FuncionarioService funcionarioService;

  public List<Ferias> findAll() {
    return feriasRepository.findAll();
  }

  public List<Ferias> findProximas() {
    return feriasRepository.findAllNextFerias();
  }

  public List<Ferias> findAprovadas() {

    List<Ferias> feriasList = feriasRepository.findAll();

    for (Ferias ferias : feriasList) {
      if (!ferias.getStatus().equals("APROVADO")) {
        feriasList.remove(ferias);
      }
    }
    return feriasList;

  }

  public List<Ferias> findByStatus(String status) {
    List<Ferias> feriasList = feriasRepository.findAll();
    List<Ferias> feriasFiltradas = new ArrayList<>();
    for (Ferias ferias : feriasList) {
      if (ferias.getStatus().equalsIgnoreCase(status)) {
        feriasFiltradas.add(ferias);

      }
    }

    return feriasFiltradas;

  }

  public List<Ferias> findByIdFuncionario(Long id) {
    return feriasRepository.findByIdFuncionario(id);
  }

  public void atualizarStatus(Long id, String status) throws NotAcceptableStatusException, NotFoundException {
    status = status.toUpperCase().trim();

    if (feriasRepository.findById(id).isEmpty()) {
      throw new NotFoundException();
    }

    if (isStatusValido(status)) {
      feriasRepository.updateFeriasStatus(status, id);
      return;
    }
    throw new NotAcceptableStatusException("Status suportados: APROVADA, VALIDADA, PENDENTE, RECUSADA, CONCLUIDA");
  }

  public List<Ferias> findByIdLider(Long id) {

    return feriasRepository.findByIdLider(id);
  }

  public List<Ferias> findByIdLiderAndStatus(Long id, String status) {
    if (isStatusValido(status)) {

      return feriasRepository.findByIdLiderAndStatus(id, status);
    }

    throw new NotAcceptableStatusException("Status suportados: APROVADA, VALIDADA, PENDENTE, RECUSADA, CONCLUIDA");
  }

  public boolean isStatusValido(String status) {
    return new Utilitario().status.contains(status.trim().toUpperCase());
  }

  public Ferias save(Ferias ferias) throws InvalidAttributesException {
    ferias.setStatus("PENDENTE");
    Funcionario funcionario = funcionarioService.getById(
        ferias.getIdFuncionario());
    ferias.setIdLider(funcionarioService.getLiderId(funcionario));
    if (isFeriasValida(ferias)) {
      // return feriasRepository.save(ferias);
    }
    return null;

  }

  private boolean isFeriasValida(Ferias ferias) {
    Date inicio = ferias.getInicio();
    Date fim = ferias.getFim();

    Calendar calendar = Calendar.getInstance();

    Assert.isTrue(inicio.before(fim), "A primeira data deve ser anterior à segunda data");

    // nao inicia na sexta
    calendar.setTime(inicio);

    Assert.isTrue(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY - 1,
        "As ferias nao podem ser iniciadas na sexta feira");

    // Assert.isTrue(Validador.isFeriadoNacional(inicio), null);
    return true;

  }

}
