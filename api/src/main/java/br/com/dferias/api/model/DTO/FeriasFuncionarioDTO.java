package br.com.dferias.api.model.DTO;

import br.com.dferias.api.model.Ferias;
import br.com.dferias.api.model.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeriasFuncionarioDTO {

    private Funcionario funcionario;
    private Ferias ferias;

}
