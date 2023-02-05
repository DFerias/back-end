package br.com.dferias.api.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Utilitario {

    public List<String> status;

    public Utilitario() {
        status = new ArrayList<>();
        status.clear();

        status.add("APROVADA");
        status.add("VALIDADA");
        status.add("RECUSADA");
        status.add("PENDENTE");
        status.add("CONCLUIDA");

    }

}
