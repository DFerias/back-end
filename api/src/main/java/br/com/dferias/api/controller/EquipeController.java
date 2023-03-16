package br.com.dferias.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dferias.api.model.Equipe;
import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.EquipeLiderDTO;
import br.com.dferias.api.repository.EquipeRepository;
import br.com.dferias.api.service.FuncionarioService;

@Controller
@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

  @Autowired
  private EquipeRepository repository;

  @Autowired
  private FuncionarioService funcionarioService;

  @GetMapping
  public ResponseEntity<List<EquipeLiderDTO>> getAll() {
    try {
      List<Equipe> items = new ArrayList<>();
      List<EquipeLiderDTO> equipes = new ArrayList<>();

      repository.findAll().forEach(items::add);

      if (items.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      for (Equipe equipe : items) {
        Funcionario lider = funcionarioService.getById(equipe.getId_lider());
        equipes.add(new EquipeLiderDTO(
            equipe, lider));

      }

      return new ResponseEntity<>(equipes, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

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
