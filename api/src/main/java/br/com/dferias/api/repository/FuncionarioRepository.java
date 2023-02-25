package br.com.dferias.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.dferias.api.model.Funcionario;

@Repository
@Component
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
  Optional<Funcionario> findByEmail(String email);

  @Modifying
  @Transactional
  @Query(value = "update Funcionario f set f.saldo_ferias = f.saldo_ferias -  ?1 where f.id = ?2", nativeQuery = false)
  void diminuirSaldoFerias(Integer quantidade, Long id);

  @Modifying
  @Transactional
  @Query(value = "update Funcionario f set f.saldo_ferias = f.saldo_ferias +  ?1 where f.id = ?2", nativeQuery = false)
  void aumentarSaldoFerias(Integer quantidade, Long id);

}
