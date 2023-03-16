package br.com.dferias.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import br.com.dferias.api.model.FuncionarioPerfis;

@Component
public interface FuncionarioPerfisRepository
        extends CrudRepository<FuncionarioPerfis, Integer> {
}
