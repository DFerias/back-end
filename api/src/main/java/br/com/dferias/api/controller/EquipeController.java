package br.com.dferias.api.controller;

import br.com.dferias.api.model.Equipe;
import br.com.dferias.api.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

  @Autowired
  private EquipeRepository repository;

  @PostMapping
  public ResponseEntity<Equipe> create(@RequestBody Equipe item) {
    try {
      Equipe savedItem = repository.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }
}
