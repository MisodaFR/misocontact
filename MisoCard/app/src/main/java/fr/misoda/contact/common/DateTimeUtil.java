package fr.misoda.contact.common;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    private DateTimeUtil() {

    }

    public static String getCurrentDateTime(Context context) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        StringBuilder dateUpdateValue = new StringBuilder(dateFormat.format(now));

        dateUpdateValue.append(StringUtils.SPACE);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        if (hour >= 10) {
            dateUpdateValue.append(hour);
        } else {
            dateUpdateValue.append("0").append(hour);
        }
        dateUpdateValue.append(":");

        if (minute >= 10) {
            dateUpdateValue.append(minute);
        } else {
            dateUpdateValue.append("0").append(minute);
        }
        dateUpdateValue.append(":");

        if (second >= 10) {
            dateUpdateValue.append(second);
        } else {
            dateUpdateValue.append("0").append(second);
        }

        return dateUpdateValue.toString();
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
