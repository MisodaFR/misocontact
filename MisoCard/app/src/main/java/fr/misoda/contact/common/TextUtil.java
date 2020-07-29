package fr.misoda.contact.common;

import org.apache.commons.lang3.StringUtils;

public class TextUtil {
    private TextUtil() {
    }

    public static String upperCaseFirstLetter(String text) {
        if (StringUtils.isNotBlank(text)) {
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        } else {
            return text;
        }
    }
}
