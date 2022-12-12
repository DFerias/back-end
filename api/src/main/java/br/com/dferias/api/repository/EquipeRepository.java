package br.com.dferias.api.repository;

import br.com.dferias.api.model.Equipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface EquipeRepository extends CrudRepository<Equipe, Long> {}
