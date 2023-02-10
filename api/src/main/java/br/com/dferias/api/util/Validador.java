package br.com.dferias.api.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class Validador {

    public static boolean isFeriadoNacional(Date data) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
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
