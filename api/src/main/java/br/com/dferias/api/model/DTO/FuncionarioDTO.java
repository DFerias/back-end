package br.com.dferias.api.model.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class FuncionarioDTO {

    private Long id;
    private Long idEquipe;
    private String nome;
    private Date dataAdmissao;
    private String email;
    private String senha;
    private String modalidade;
    private String cidade;
    private String uf;

}
