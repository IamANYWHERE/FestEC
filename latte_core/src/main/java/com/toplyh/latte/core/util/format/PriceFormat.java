package com.toplyh.latte.core.util.format;

import java.text.DecimalFormat;

public class PriceFormat {

    public static final String formatPrice(double price){
        final DecimalFormat df = new DecimalFormat("#.00");
        return df.format(price);
    }
}
