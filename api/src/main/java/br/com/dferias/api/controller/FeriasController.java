package br.com.dferias.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.NotAcceptableStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.FeriasFuncionarioDTO;
import br.com.dferias.api.model.DTO.TokenDTO;
import br.com.dferias.api.service.AuthenticationService;
import br.com.dferias.api.service.FeriasService;
import br.com.dferias.api.service.FuncionarioService;
import io.micrometer.common.lang.Nullable;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api")
@Controller
@Slf4j
public class FeriasController {

  @Autowired
  private FeriasService feriasService;
  @Autowired
  private AuthenticationService authService;

  @Autowired
  private FuncionarioService funcionarioService;

  @PostMapping("/ferias")
  public Object create(@RequestBody Ferias ferias) {
    log.warn(ferias.toString());
    try {
      Ferias savedItem = feriasService.save(ferias);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"erro\":\"" + e.getMessage() + "\"}");
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

  @GetMapping("/ferias/lider")
  public ResponseEntity<List<FeriasFuncionarioDTO>> getByLider(@RequestHeader String authorization) {

    List<Ferias> feriasList = new ArrayList<>();
    List<FeriasFuncionarioDTO> feriasFuncionario = new ArrayList<>();
    try {
      authorization = authorization.replaceAll("Bearer ", "");
      Long id = authService.getIdByToken(new TokenDTO("Bearer", authorization));
      feriasList = feriasService.findByIdLider(id);

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

  @GetMapping("/ferias/lider/{status}")
  public ResponseEntity<List<FeriasFuncionarioDTO>> getByLiderAndStatus(@RequestHeader String authorization,
      @PathVariable String status) {

    if (status == null) {
      status = "todos";
    }
    List<Ferias> feriasList = new ArrayList<>();
    List<FeriasFuncionarioDTO> feriasFuncionario = new ArrayList<>();
    try {
      authorization = authorization.replaceAll("Bearer ", "");
      Long id = authService.getIdByToken(new TokenDTO("Bearer", authorization));
      feriasList = feriasService.findByIdLiderAndStatus(id, status);

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

  @PostMapping("/ferias/{id}/{status}")
  public ResponseEntity<String> update(@PathVariable Long id, @PathVariable String status,
      @Nullable @RequestBody String requestBody) {
    try {

      log.info(requestBody);
      feriasService.atualizarStatus(id, status);
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, String> jsonMap = objectMapper.readValue(requestBody, new TypeReference<Map<String, String>>() {
      });

      String comentarioLider = jsonMap.get("comentarioLider");
      String comentarioRH = jsonMap.get("comentarioRH");

      if (comentarioLider != null) {
        log.info(comentarioLider);
        feriasService.adicionarComentarioLider(id, comentarioLider);
      }

      if (comentarioRH != null) {
        log.info(comentarioRH);
        feriasService.adicionarComentarioRh(id, comentarioRH);
      }
      return new ResponseEntity<>("ok", HttpStatus.OK);
    } catch (NotAcceptableStatusException ex) {
      return new ResponseEntity<>("Status nao suportado\n" + ex.getMessage(), HttpStatus.BAD_REQUEST);

    } catch (NotFoundException e) {
      return new ResponseEntity<>("ID nao encontrado",
          HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>("Erro ao atualizar " + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
