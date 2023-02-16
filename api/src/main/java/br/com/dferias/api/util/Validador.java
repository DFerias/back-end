package br.com.dferias.api.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dferias.api.model.Funcionario;
import br.com.dferias.api.service.FuncionarioService;

@Service
public class Validador {
    @Autowired
    private FuncionarioService funcionarioService;

    public int getDiferencaEntreDatas(Date startDate, Date endDate) {
        long ms = Math.abs(endDate.getTime() - startDate.getTime());
        long dias = TimeUnit.DAYS.convert(ms, TimeUnit.MILLISECONDS);
        return (int) dias;
    }

    public boolean isQuantidadeFeriasValido(Long id, Integer quantidade) {

        Funcionario funcionario = funcionarioService.findById(id).get();
        return funcionario.getSaldoFerias() >= quantidade;

    }

    public static boolean isFeriadoNacional(Date data) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH) + 1;

        return (mes == Calendar.JANUARY && dia == 1) ||
                (mes == Calendar.APRIL && dia == 21) ||
                (mes == Calendar.MAY && dia == 1) ||
                (mes == Calendar.JUNE && dia == 12) ||
                (mes == Calendar.SEPTEMBER && dia == 7) ||
                (mes == Calendar.OCTOBER && dia == 12) ||
                (mes == Calendar.NOVEMBER && dia == 2) ||
                (mes == Calendar.DECEMBER && dia == 25);
    }

}
