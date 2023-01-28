package br.com.dferias.api.model.DTO;

import br.com.dferias.api.model.Equipe;
import br.com.dferias.api.model.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EquipeLiderDTO {

    private Equipe equipe;
    private Funcionario lider;
}
