package br.com.dferias.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.dferias.api.model.Ferias;

@Repository
public interface FeriasRepository extends JpaRepository<Ferias, Long> {
    List<Ferias> findByIdFuncionario(Long idFuncionario);

    @Query(value = "SELECT * FROM ferias  WHERE inicio > DATE_SUB(NOW(), INTERVAL 1 DAY) ORDER BY inicio ASC;", nativeQuery = true)
    List<Ferias> findAllNextFerias();
}