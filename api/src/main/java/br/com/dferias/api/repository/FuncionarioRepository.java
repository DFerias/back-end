package br.com.dferias.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import br.com.dferias.api.model.Funcionario;

@Repository
@Component
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
  Optional<Funcionario> findByEmail(String email);

}
