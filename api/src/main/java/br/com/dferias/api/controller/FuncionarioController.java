package br.com.dferias.api.controller;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.FuncionarioDTO;
import br.com.dferias.api.repository.FuncionarioRepository;
import br.com.dferias.api.service.FuncionarioService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api")
public class FuncionarioController {

  @Autowired
  private FuncionarioRepository repository;

  @Autowired
  private FuncionarioService funcionarioService;

  @GetMapping("/users")
  public ResponseEntity<List<Funcionario>> getAll() {
    try {
      List<Funcionario> items = new ArrayList<Funcionario>();

      repository.findAll().forEach(items::add);

      if (items.isEmpty())
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<Funcionario> getAll(@PathVariable Long id) {

    try {

      Funcionario funcionario = repository.findById(id).get();

      return new ResponseEntity<>(funcionario, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("user/new")
  @ResponseBody
  public Object createUser(@RequestBody FuncionarioDTO funcionario) {
    funcionarioService.createFuncionario(new Funcionario(funcionario));
    return ResponseEntity.ok().body("ok");
  }

}
