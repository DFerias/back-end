package br.com.dferias.api.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.dferias.api.model.DTO.FuncionarioDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Funcionario {

    public Funcionario(FuncionarioDTO funcionarioDTO) {
        this.id = funcionarioDTO.getId();
        this.idEquipe = funcionarioDTO.getIdEquipe();
        this.nome = funcionarioDTO.getNome();
        this.dataAdmissao = funcionarioDTO.getDataAdmissao();
        this.email = funcionarioDTO.getEmail();
        this.saldoFerias = 0;
        this.senha = funcionarioDTO.getSenha();
        this.modalidade = funcionarioDTO.getModalidade();
        this.cidade = funcionarioDTO.getCidade();
        this.uf = funcionarioDTO.getUf();

    }

    @Id
    private int id;
    private int idEquipe;
    private String nome;
    private Date dataAdmissao;
    private String email;
    private int saldoFerias;
    private String senha;
    private String modalidade;
    private String cidade;
    private String uf;

}
