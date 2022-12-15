package br.com.dferias.api.controller;

import br.com.dferias.api.model.FuncionarioPerfis;
import br.com.dferias.api.model.Perfil;
import br.com.dferias.api.repository.FuncionarioPerfisRepository;
import br.com.dferias.api.repository.PerfilRepository;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

  @Autowired
  private PerfilRepository repository;

  @Autowired
  private FuncionarioPerfisRepository funcionarioPerfisRepository;

  @GetMapping
  public ResponseEntity<List<Perfil>> getAll() {
    try {
      List<Perfil> items = new ArrayList<Perfil>();

      repository.findAll().forEach(items::add);

      if (items.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<Perfil> create(@RequestBody Perfil item) {
    try {
      Perfil savedItem = repository.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PostMapping("/atualizar")
  @ResponseBody
  public Object changeRole(@RequestBody FuncionarioPerfis funcionarioPerfil) {
    funcionarioPerfisRepository.save(funcionarioPerfil);
    return ResponseEntity.ok().body("ok");
  }
}
