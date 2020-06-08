package com.apuyinc.lana.pricecalculator.helpers;

import java.math.BigDecimal;

public class Converter {

    public static BigDecimal ConvertPriceStringToNumeric(String currencyString) {
        return new BigDecimal(currencyString.substring(0, currencyString.length()-2));
    }
}
