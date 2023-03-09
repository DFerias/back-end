package br.com.dferias.api.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static Date arrumarData(Date data) throws ParseException {

        Calendar cal = Calendar.getInstance();

        cal.setTime(data);

        // cal.add(Calendar.DATE, 1);

        return new Date(cal.getTimeInMillis());

    }

}
