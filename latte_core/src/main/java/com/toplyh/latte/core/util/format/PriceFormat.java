package com.toplyh.latte.core.util.format;


import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormat {

    public static String formatPrice(double price){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.CHINA);
        return formatter.format(price);
    }
}
