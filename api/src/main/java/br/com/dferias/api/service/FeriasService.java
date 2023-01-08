package br.com.dferias.api.service;

import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.repository.FeriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeriasService {

  @Autowired
  private FeriasRepository feriasRepository;

  public Ferias save(Ferias ferias) {
    return feriasRepository.save(ferias);
  }
}
