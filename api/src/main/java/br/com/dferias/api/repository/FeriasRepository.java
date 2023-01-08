package br.com.dferias.api.repository;

import br.com.dferias.api.model.Ferias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeriasRepository extends JpaRepository<Ferias, Long> {}
