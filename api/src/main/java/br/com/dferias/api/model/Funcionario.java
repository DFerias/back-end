package br.com.dferias.api.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Funcionario {

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
