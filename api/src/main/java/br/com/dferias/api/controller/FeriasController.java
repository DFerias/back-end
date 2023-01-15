package br.com.dferias.api.controller;

import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.service.FeriasService;
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

@RequestMapping("/api")
@Controller
public class FeriasController {

  @Autowired
  private FeriasService feriasService;

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
  public ResponseEntity<List<Ferias>> getAll() {
    try {
      List<Ferias> ferias = new ArrayList<Ferias>();

      ferias = feriasService.findAll();

      if (ferias.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(ferias, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
