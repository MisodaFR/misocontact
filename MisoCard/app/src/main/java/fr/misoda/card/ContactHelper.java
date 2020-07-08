package fr.misoda.card;

import org.apache.commons.lang3.StringUtils;

public class ContactHelper {
    private ContactHelper() {
    }

    public static String getEmail(String text) {
        if (!text.contains("@")) {
            return StringUtils.EMPTY;
        }

        String[] strings = text.split("\\s+");
        for (String s : strings) {
            if (s.contains("@")) {
                return s;
            }
        }

        return StringUtils.EMPTY;
    }
}
