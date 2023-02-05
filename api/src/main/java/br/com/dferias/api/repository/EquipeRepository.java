package br.com.dferias.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import br.com.dferias.api.model.Equipe;

@Repository
@Component
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}
