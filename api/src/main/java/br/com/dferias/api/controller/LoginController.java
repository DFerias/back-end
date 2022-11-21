package br.com.dferias.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.dferias.api.DTO.LoginDTO;
import br.com.dferias.api.DTO.TokenDTO;
import br.com.dferias.api.service.LoginService;

@RequestMapping("/login")
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody LoginDTO item) {
        try {
            TokenDTO token = loginService.enviarToken(item);
            return new ResponseEntity<>(null, HttpStatus.CREATED).ok("ok");
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

}
