package br.com.dferias.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import br.com.dferias.api.model.Equipe;

@Component
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}
