package br.com.dferias.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDTO {

    private String token;
    private String tipo;
    private FuncionarioDTO funcionario;

}
