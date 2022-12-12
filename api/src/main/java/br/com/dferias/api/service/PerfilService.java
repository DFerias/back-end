package br.com.dferias.api.service;

import br.com.dferias.api.model.FuncionarioPerfis;
import br.com.dferias.api.repository.FuncionarioPerfisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

  @Autowired
  private FuncionarioPerfisRepository repository;

  public void vincularNovoPerfil(Long idNovoFuncionario) {
    System.out.println("Salvando " + idNovoFuncionario);
    FuncionarioPerfis vinculo = new FuncionarioPerfis(
      Integer.parseInt(idNovoFuncionario.toString()),
      1
    );
    System.out.println(vinculo);
    repository.save(vinculo);
  }
}
