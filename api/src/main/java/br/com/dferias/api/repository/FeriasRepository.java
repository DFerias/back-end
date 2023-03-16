package br.com.dferias.api.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.dferias.api.model.Ferias;

public interface FeriasRepository extends JpaRepository<Ferias, Long> {
    List<Ferias> findByIdFuncionario(Long idFuncionario);

    List<Ferias> findByIdLider(Long id);

    List<Ferias> findByIdLiderAndStatus(Long id, String status);

    @Query(value = "SELECT * FROM ferias  WHERE inicio > DATE_SUB(NOW(), INTERVAL 1 DAY) ORDER BY inicio ASC;", nativeQuery = true)
    List<Ferias> findAllNextFerias();

    @Modifying
    @Transactional
    @Query(value = "update Ferias f set f.status = ?1 where f.id = ?2", nativeQuery = false)
    void updateFeriasStatus(String status, Long id);

    @Modifying
    @Transactional
    @Query(value = "update Ferias f set f.observacoes_lider = ?2 where f.id = ?1", nativeQuery = false)
    void updateLiderComentario(Long idFerias, String comentario);

    @Modifying
    @Transactional
    @Query(value = "update Ferias f set f.observacoes_rh = ?2 where f.id = ?1", nativeQuery = false)
    void updateRHComentario(Long idFerias, String comentario);

    @Query(value = "SELECT f.id FROM cidade c join feriado_municipal f on f.cidade_id = c.id and c.nome = ?1 and c.estado =?2  and f.dia= ?3 and f.mes =?4 limit 1", nativeQuery = true)
    List<Object> validarFeriadoMunicipal(String cidade, String uf, String dia, String mes);
}