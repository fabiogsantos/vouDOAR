package br.com.tcc.tecdam.voudoar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by fabio.goncalves on 19/04/2018.
 */

public class DataUtil {

    public static final String MASCARA_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String IDIOMA_PT = "pt";
    public static final String PAIS_BR = "BR";

    public static Date toDate(String data) {
        if (data == null || data.isEmpty()) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(MASCARA_DD_MM_YYYY, new Locale(IDIOMA_PT, PAIS_BR));
        Date dataConvert = null;
        try {
            dataConvert = new Date(sdf.parse(data).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataConvert;
    }

    public static Date toDate(int year, int month, int dayOfMonth) {
        Calendar converDatePicker = java.util.Calendar.getInstance();
        converDatePicker.set(year, month, dayOfMonth);
        return new Date(converDatePicker.getTimeInMillis());
    }

    public static String toString(Date data) {
        return new SimpleDateFormat(MASCARA_DD_MM_YYYY).format(data);
    }

    public static Calendar toCalendar(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar;
    }
}
