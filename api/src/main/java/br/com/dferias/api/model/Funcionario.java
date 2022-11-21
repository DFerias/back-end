package br.com.dferias.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "funcionario")
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {
    @Id
    private Long id;
    private String nome;
    private String email;
    private Date dataAdmissao;
    private int saldoDeFerias;
    private int idEquipe;
    private String senha;
    private String modalidade;
    private String cidade;
    private String uf;

    public boolean isAdministrador() {
        return false;
    }

}