package fr.misoda.card;

import org.apache.commons.lang3.StringUtils;

public class ContactHelper {
    private ContactHelper() {
    }

    public static String getEmail(String text) {
        if (!text.contains("@")) {
            return StringUtils.EMPTY;
        }

        // Split by white space characters (inclut break line, ...)
        String[] strings = text.split("\\s+");
        for (String s : strings) {
            if (s.contains("@")) {
                return s;
            }
        }

        return StringUtils.EMPTY;
    }

    public static String getName(String text) {
        String copyText = text;
        // Replace all break line character by single break line character (Thay nhung ki tu xuong dong bang xuong dong don)
        copyText = copyText.replaceAll("[\n\\x0B]+[\\s*]", "\n");
        String[] strings = copyText.split("\n");
        for (String s : strings) {
            boolean isContainDigit = StringUtils.isNotEmpty(StringUtils.getDigits(s));
            if (StringUtils.isNotBlank(s) && !s.contains("@") && !isContainDigit) {
                return s;
            }
        }
        return StringUtils.EMPTY;
    }
}
