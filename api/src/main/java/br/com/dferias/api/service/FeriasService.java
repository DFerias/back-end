package br.com.dferias.api.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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

  @Autowired
  private Validador validador;

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
    Optional<Ferias> feriasOpt = feriasRepository.findById(id);
    if (feriasOpt.isEmpty()) {
      throw new NotFoundException();
    }

    if (isStatusValido(status)) {
      Ferias ferias = feriasOpt.get();
      if (status.toUpperCase().trim().equals("RECUSADA")) {
        funcionarioService.devolverSaldo(ferias);
      }
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

  public Ferias save(Ferias ferias) throws InvalidAttributesException, ParseException {
    ferias.setStatus("PENDENTE");
    Funcionario funcionario = funcionarioService.getById(
        ferias.getIdFuncionario());
    ferias.setIdLider(funcionarioService.getLiderId(funcionario));

    if (isFeriasValida(ferias)) {

      funcionarioService.diminuirSaldo(ferias);
      return feriasRepository.save(ferias);
    }
    return null;

  }

  private int getQuantidadePeriodosSolicitados(Long idFuncionario) {
    int quantidade = 1;
    for (Ferias ferias : findByIdFuncionario(idFuncionario)) {
      if (!ferias.getStatus().equals("RECUSADA") && !ferias.getStatus().equals("CONCLUIDA")) {

        quantidade++;
      }
    }
    return quantidade;

  }

  private boolean isFeriasValida(Ferias ferias) {
    Calendar calendar = Calendar.getInstance();

    Date inicio = ferias.getInicio();
    Date fim = ferias.getFim();
    int quantidade = validador.getDiferencaEntreDatas(inicio, fim);
    Funcionario funcionario = funcionarioService.getById(ferias.getIdFuncionario());

    Assert.isTrue(inicio.before(fim), "A primeira data deve ser anterior à segunda data");

    Assert.isTrue(quantidade >= 5, "As férias nao podem ser inferiores à cinco dias");

    Assert.isTrue((funcionario.getSaldo_ferias() - quantidade) >= 5,
        "Por favor, informe um período mais curto ou o período completo, já que o período atual deixaria menos de 5 dias restantes.");

    calendar.setTime(inicio);
    Assert.isTrue(validador.isQuantidadeFeriasValido(ferias.getIdFuncionario(), quantidade),
        "O funcionario nao tem saldo de ferias suficiente");

    Assert.isTrue(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY,
        "As ferias nao podem ser iniciadas na sexta feira");
    Assert.isTrue(!Validador.isFeriadoNacional(inicio), "As férias nao podem ser iniciadas em um feriado");

    boolean isFeriadoMunicipal = true;

    Integer dia = inicio.getDate();
    Integer mes = inicio.getMonth() + 1;
    try {

      log.info(dia + "/" + mes);
      feriasRepository.validarFeriadoMunicipal(funcionario.getCidade(), funcionario.getUf(),
          dia.toString(),
          mes.toString()).get(0);

    } catch (Exception e) {
      isFeriadoMunicipal = false;
    }

    Assert.isTrue(
        !isFeriadoMunicipal,
        dia + "/" + mes + "  é um feriado municipal na cidade onde o funcionário esta alocado");

    int quantidadeDePeriodosSolicitados = getQuantidadePeriodosSolicitados(ferias.getIdFuncionario());

    Assert.isTrue(
        quantidadeDePeriodosSolicitados <= 3,
        "O funcionário ja tem 3 periodos solicitados");

    if (quantidadeDePeriodosSolicitados == 2) {
      Assert.isTrue(funcionario.getSaldo_ferias() == quantidade,
          "Como o funcionário já tem 2 períodos de férias agendados, o último período deve ter a duração total do saldo de férias restante: "
              + funcionario.getSaldo_ferias() + " dias ");

    }
    List<Ferias> listaDeFerias = findByIdFuncionario(ferias.getIdFuncionario());
    boolean possuiPeriodoGrandeAgendado = false;
    if (quantidadeDePeriodosSolicitados == 1) {
      for (Ferias solicitacao : listaDeFerias) {
        if (!solicitacao.getStatus().equals("RECUSADA") && !solicitacao.getStatus().equals("CONCLUIDA")) {
          if (validador.getDiferencaEntreDatas(solicitacao.getInicio(), solicitacao.getFim()) >= 14) {
            possuiPeriodoGrandeAgendado = true;
          }
          ;
        }

      }
      if (!possuiPeriodoGrandeAgendado && quantidade < 14) {

        Assert.isTrue(funcionario.getSaldo_ferias() - quantidade >= 14,
            "Um dos periodos deve ter 14 ou mais dias, portanto essa solicitacao forcaria 3 periodos menores que 14");
      }
    }
    return true;
  }

  public void adicionarComentarioLider(Long idFerias, String comentario) throws NotFoundException {

    if (feriasRepository.findById(idFerias).isEmpty()) {
      throw new NotFoundException();
    }
    feriasRepository.updateLiderComentario(idFerias, comentario);
  }

  public void adicionarComentarioRh(Long idFerias, String comentario) throws NotFoundException {

    if (feriasRepository.findById(idFerias).isEmpty()) {
      throw new NotFoundException();
    }
    feriasRepository.updateRHComentario(idFerias, comentario);
  }

}
