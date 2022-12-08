package br.com.dferias.api.model.DTO;

import java.sql.Date;

import br.com.dferias.api.model.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class FuncionarioDTO {

    public FuncionarioDTO(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.idEquipe = funcionario.getIdEquipe();
        this.nome = funcionario.getNome();
        this.dataAdmissao = funcionario.getDataAdmissao();
        this.email = funcionario.getEmail();
        this.modalidade = funcionario.getModalidade();
        this.cidade = funcionario.getCidade();
        this.uf = funcionario.getUf();

    }

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
