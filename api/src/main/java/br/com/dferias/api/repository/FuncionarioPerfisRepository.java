package br.com.dferias.api.repository;

import br.com.dferias.api.model.FuncionarioPerfis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface FuncionarioPerfisRepository
    extends CrudRepository<FuncionarioPerfis, Integer> {
}
