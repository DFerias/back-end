package br.com.dferias.api.controller;

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

import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.FeriasFuncionarioDTO;
import br.com.dferias.api.service.FeriasService;
import br.com.dferias.api.service.FuncionarioService;

@RequestMapping("/api")
@Controller
public class FeriasController {

  @Autowired
  private FeriasService feriasService;

  @Autowired
  private FuncionarioService funcionarioService;

  @PostMapping("/ferias")
  public ResponseEntity<Ferias> create(@RequestBody Ferias ferias) {
    try {
      Ferias savedItem = feriasService.save(ferias);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/ferias")
  public ResponseEntity<List<FeriasFuncionarioDTO>> getAll() {
    List<Ferias> feriasList = new ArrayList<>();
    List<FeriasFuncionarioDTO> feriasFuncionario = new ArrayList<>();
    try {

      feriasList = feriasService.findAll();

      if (feriasList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      for (Ferias ferias : feriasList) {
        Funcionario funcionario = funcionarioService.getById(ferias.getIdFuncionario());

        feriasFuncionario.add(new FeriasFuncionarioDTO(funcionario, ferias));
      }
      return new ResponseEntity<>(feriasFuncionario, HttpStatus.OK);
    } catch (Exception e) {

      return new ResponseEntity<>(feriasFuncionario, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/ferias/inicial")
  public ResponseEntity<List<FeriasFuncionarioDTO>> getProximas() {
    List<Ferias> feriasList = new ArrayList<>();
    List<FeriasFuncionarioDTO> feriasFuncionario = new ArrayList<>();
    try {

      feriasList = feriasService.findProximas();

      if (feriasList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      for (Ferias ferias : feriasList) {
        Funcionario funcionario = funcionarioService.getById(ferias.getIdFuncionario());

        feriasFuncionario.add(new FeriasFuncionarioDTO(funcionario, ferias));
      }
      return new ResponseEntity<>(feriasFuncionario, HttpStatus.OK);
    } catch (Exception e) {

      return new ResponseEntity<>(feriasFuncionario, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/ferias/{status}")
  public ResponseEntity<List<FeriasFuncionarioDTO>> getAprovadas(@PathVariable String status) {
    List<Ferias> feriasList = new ArrayList<>();
    List<FeriasFuncionarioDTO> feriasFuncionario = new ArrayList<>();
    try {

      feriasList = feriasService.findByStatus(status);

      if (feriasList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      for (Ferias ferias : feriasList) {
        Funcionario funcionario = funcionarioService.getById(ferias.getIdFuncionario());

        feriasFuncionario.add(new FeriasFuncionarioDTO(funcionario, ferias));
      }
      return new ResponseEntity<>(feriasFuncionario, HttpStatus.OK);
    } catch (Exception e) {

      return new ResponseEntity<>(feriasFuncionario, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/ferias/funcionario/{idFuncionario}")
  public ResponseEntity<List<Ferias>> getFuncionarioFerias(@PathVariable String idFuncionario) {
    List<Ferias> feriasList = new ArrayList<>();

    try {

      feriasList = feriasService.findByIdFuncionario(Long.valueOf(idFuncionario));

      if (feriasList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(feriasList, HttpStatus.OK);
    } catch (Exception e) {

      return new ResponseEntity<>(feriasList, HttpStatus.NOT_FOUND);
    }
  }

}
