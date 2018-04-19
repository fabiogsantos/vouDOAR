package br.com.tcc.tecdam.voudoar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fabio.goncalves on 19/04/2018.
 */

public class DataUtil {

    public static final String MASCARA_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String IDIOMA_PT = "pt";
    public static final String PAIS_BR = "BR";

    public static Date toDate(String data) {
        final Locale localeBrasil = new Locale(IDIOMA_PT, PAIS_BR);
        String myFormat = MASCARA_DD_MM_YYYY; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, localeBrasil);
        Date dataConvert = null;
        try {
            dataConvert = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataConvert;
    }

    public static String toString(Date data) {
        final Locale localeBrasil = new Locale(IDIOMA_PT, PAIS_BR);
        String myFormat = MASCARA_DD_MM_YYYY; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, localeBrasil);
        return sdf.format(data);
    }
}
