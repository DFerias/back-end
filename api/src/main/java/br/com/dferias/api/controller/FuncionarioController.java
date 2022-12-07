package br.com.dferias.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.repository.FuncionarioRepository;

@Controller
@RestController
@RequestMapping("/api")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository repository;

    @PostMapping("/user")
    public ResponseEntity<List<Funcionario>> getAll() {
        try {
            List<Funcionario> items = new ArrayList<Funcionario>();
            System.out.println("buscando");
            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Funcionario>> getAllAdmin() {
        try {
            List<Funcionario> items = new ArrayList<Funcionario>();
            System.out.println("buscando");
            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
