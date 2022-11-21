package br.com.dferias.api.DTO;

import java.util.Date;

import javax.persistence.Id;

import br.com.dferias.api.model.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {

    @Id
    private Long id;
    private String nome;
    private String email;
    private Date dataAdmissao;
    private int saldoDeFerias;
    private int idEquipe;
    private String modalidade;
    private String cidade;
    private String uf;

    public boolean isAdministrador() {
        return false;
    }

    public FuncionarioDTO convertToDTO(Funcionario funcionario) {
        return new FuncionarioDTO(funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getDataAdmissao(),
                funcionario.getSaldoDeFerias(),
                funcionario.getIdEquipe(),
                funcionario.getModalidade(),
                funcionario.getCidade(),
                funcionario.getUf());
    }
}