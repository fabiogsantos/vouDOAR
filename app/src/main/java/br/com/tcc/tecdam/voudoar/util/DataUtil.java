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
        SimpleDateFormat sdf = new SimpleDateFormat(MASCARA_DD_MM_YYYY, new Locale(IDIOMA_PT, PAIS_BR));
        Date dataConvert = null;
        try {
            dataConvert = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataConvert;
    }

    public static String toString(Date data) {
        return new SimpleDateFormat(MASCARA_DD_MM_YYYY).format(data);
    }
}
