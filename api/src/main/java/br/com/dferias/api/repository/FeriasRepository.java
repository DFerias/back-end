package br.com.dferias.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.dferias.api.model.Ferias;

@Repository
public interface FeriasRepository extends JpaRepository<Ferias, Long> {
    List<Ferias> findByIdFuncionario(Long idFuncionario);

    List<Ferias> findByIdLider(Long id);

    @Query(value = "SELECT * FROM ferias  WHERE inicio > DATE_SUB(NOW(), INTERVAL 1 DAY) ORDER BY inicio ASC;", nativeQuery = true)
    List<Ferias> findAllNextFerias();

    @Modifying
    @Transactional
    @Query(value = "update Ferias f set f.status = ?1 where f.id = ?2", nativeQuery = false)
    void updateFeriasStatus(String status, Long id);

}