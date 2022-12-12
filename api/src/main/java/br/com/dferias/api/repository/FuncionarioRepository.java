package br.com.dferias.api.repository;

import br.com.dferias.api.model.Funcionario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface FuncionarioRepository
  extends CrudRepository<Funcionario, Long> {
  Optional<Funcionario> findByEmail(String email);
}
