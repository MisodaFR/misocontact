package fr.misoda.contact.common;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getLinesContainPhone(String text) {
        String copyText = text;
        // Replace all break line character by single break line character (Thay nhung ki tu xuong dong bang xuong dong don)
        copyText = copyText.replaceAll("[\n\\x0B]+[\\s*]", "\n");
        String[] strings = copyText.split("\n");
        List<String> phones = new ArrayList<>();
        for (String s : strings) {
            if (s.contains("Direct :") || s.contains("Direct:") || s.contains("Mobile :") || s.contains("Mobile:")) {
                phones.add(s);
                continue;
            }
            String digits = StringUtils.getDigits(s);
            if (StringUtils.isNoneEmpty(digits) && digits.length() >= 10) {
                phones.add(s);
            }
        }
        return phones;
    }

    public static String getWorkPhone(String text) {
        List<String> linesContainPhone = getLinesContainPhone(text);
        if (linesContainPhone.isEmpty()) {
            return StringUtils.EMPTY;
        }
        String lineResult = StringUtils.EMPTY;
        for (String s : linesContainPhone) {
            if (s.contains("Direct :") || s.contains("Direct:")) {
                lineResult = s;
                break;
            }
        }

        if (StringUtils.isEmpty(lineResult)) {
            lineResult = linesContainPhone.get(0);
        }

        String digits = StringUtils.getDigits(lineResult);
        if (StringUtils.isEmpty(digits)) {
            return StringUtils.EMPTY;
        }
        return digits.startsWith("0") ? digits : "+" + digits;
    }

    public static String getMobilePhone(String text) {
        List<String> linesContainPhone = getLinesContainPhone(text);
        if (linesContainPhone.isEmpty()) {
            return StringUtils.EMPTY;
        }
        String lineResult = StringUtils.EMPTY;
        for (String s : linesContainPhone) {
            if (s.contains("Mobile :") || s.contains("Mobile:")) {
                lineResult = s;
                break;
            }
        }

        if (StringUtils.isEmpty(lineResult) && linesContainPhone.size() >= 2) {
            lineResult = linesContainPhone.get(1);
        }

        String digits = StringUtils.getDigits(lineResult);
        if (StringUtils.isEmpty(digits)) {
            return StringUtils.EMPTY;
        }
        return digits.startsWith("0") ? digits : "+" + digits;
    }
}
