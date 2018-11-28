package com.toplyh.latte.core.util.format;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateFormat {

    public static String formatDate(int year,int monthOfYear,int dayOfMonth){
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        final String data = format.format(calendar.getTime());
        return data;
    }
}
