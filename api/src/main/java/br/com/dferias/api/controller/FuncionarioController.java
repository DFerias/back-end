package br.com.dferias.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.repository.FuncionarioRepository;

@Controller

@RequestMapping("/funcionario")
@EnableAutoConfiguration
class resourceNameController {

    @Autowired
    FuncionarioRepository repository;

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAll() {
        try {
            List<Funcionario> items = new ArrayList<>();

            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {

            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Funcionario> getById(@PathVariable("id") Long id) {
        Optional<Funcionario> existingFuncionarioOptional = repository.findById(id);

        if (existingFuncionarioOptional.isPresent()) {
            return new ResponseEntity<>(existingFuncionarioOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Funcionario> create(@RequestBody Funcionario funcionario) {
        try {
            Funcionario savedFuncionario = repository.save(funcionario);
            return new ResponseEntity<>(savedFuncionario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Funcionario> update(@PathVariable("id") Long id,
            @RequestBody Funcionario funcionario) {
        Optional<Funcionario> existingFuncionarioOptional = repository.findById(id);
        if (existingFuncionarioOptional.isPresent()) {
            Funcionario existingFuncionario = existingFuncionarioOptional.get();
            System.out
                    .println("TODO for developer - update logic is unique to entity and must be implemented manually.");
            // existingFuncionario.setSomeField(funcionario.getSomeField());
            return new ResponseEntity<>(repository.save(existingFuncionario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}