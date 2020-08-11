package fr.misoda.contact.common;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    private DateTimeUtil() {

    }

    public static String getUpdateDate(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Constant.UPDATE_YEAR);
        calendar.set(Calendar.MONTH, Constant.UPDATE_MONTH); // Le mois est basé sur 0
        calendar.set(Calendar.DATE, Constant.UPDATE_DAY);
        Date date = calendar.getTime();
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        return dateFormat.format(date);
    }
}
