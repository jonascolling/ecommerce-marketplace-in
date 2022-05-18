package com.ecommerce.marketplacein.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.text.Normalizer;

public class GenericUtils {

    public static String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", Strings.EMPTY);
    }

    public static String removeSpecialCharacters(final String string) {
        if (StringUtils.isNotEmpty(string)) {
            return string.replaceAll("[^0-9a-zA-Z]+", "");
        }
        return StringUtils.EMPTY;
    }

}
