package br.com.dferias.api.controller;

import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.service.FeriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class FeriasController {

  @Autowired
  private FeriasService feriasService;

  @PostMapping
  public ResponseEntity<Ferias> create(@RequestBody Ferias ferias) {
    try {
      Ferias savedItem = feriasService.save(ferias);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }
}
