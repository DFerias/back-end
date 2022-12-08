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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.model.DTO.FuncionarioDTO;
import br.com.dferias.api.repository.FuncionarioRepository;
import br.com.dferias.api.service.FuncionarioService;

@Controller
@RestController
@RequestMapping("/api")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/user")
    public ResponseEntity<List<Funcionario>> getAll() {
        System.out.println("REMOVA ESSE METODO");
        try {
            List<Funcionario> items = new ArrayList<Funcionario>();
            System.out.println("buscando");
            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.I_AM_A_TEAPOT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/new")
    @ResponseBody
    public Object createUser(@RequestBody FuncionarioDTO funcionario) {
     
        funcionarioService.createFuncionario(new Funcionario(funcionario));
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/admin")
    public ResponseEntity<List<FuncionarioDTO>> getAllAdmin() {
        try {
            List<FuncionarioDTO> items = new ArrayList<FuncionarioDTO>();
            System.out.println("buscando");

            for (Funcionario funcionario : repository.findAll()) {
                items.add(new FuncionarioDTO(funcionario));
            }

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
