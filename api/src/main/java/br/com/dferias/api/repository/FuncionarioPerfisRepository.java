package br.com.dferias.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import br.com.dferias.api.model.FuncionarioPerfis;

@Repository
@Component
public interface FuncionarioPerfisRepository extends CrudRepository<FuncionarioPerfis, Integer> {

}
