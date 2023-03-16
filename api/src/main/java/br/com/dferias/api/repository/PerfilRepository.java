package br.com.dferias.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dferias.api.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
}
