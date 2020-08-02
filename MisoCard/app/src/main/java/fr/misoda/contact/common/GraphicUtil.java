package fr.misoda.contact.common;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.lang3.StringUtils;

public class GraphicUtil {

    private GraphicUtil() {
    }

    // dp to pixel(s)
    public static int dpToPx(float dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi * 1.f / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static int getForegroundWhiteOrBlack(int backgroundColor) {
        int blue = Color.blue(backgroundColor);
        int green = Color.green(backgroundColor);
        int red = Color.red(backgroundColor);
        double luminanceGris = (0.2126 * red) + (0.7152 * green) + (0.0722 * blue);
        if (luminanceGris < 128) {
            return Color.WHITE;
        }
        return Color.BLACK;
    }

    public static int hexColorValueARGBToInt(String hexColorValue) throws IllegalArgumentException {
        if (!isHexColorValueARGBValid(hexColorValue)) {
            throw new IllegalArgumentException();
        }
        String hexColorValue8Chars = convertToHexColorValueARGBChars(hexColorValue);
        int a = Integer.parseInt(hexColorValue8Chars.substring(0, 2), 16);
        int r = Integer.parseInt(hexColorValue8Chars.substring(2, 4), 16);
        int g = Integer.parseInt(hexColorValue8Chars.substring(4, 6), 16);
        int b = Integer.parseInt(hexColorValue8Chars.substring(6, 8), 16);
        return Color.argb(a, r, g, b);
    }

    public static int hexColorValueRGBToInt(String hexColorValue) throws IllegalArgumentException {
        if (!isHexColorValueRGBValid(hexColorValue)) {
            throw new IllegalArgumentException();
        }
        String hexColorValue6Chars = convertToHexColorValueRGBChars(hexColorValue);
        int r = Integer.parseInt(hexColorValue6Chars.substring(0, 2), 16);
        int g = Integer.parseInt(hexColorValue6Chars.substring(2, 4), 16);
        int b = Integer.parseInt(hexColorValue6Chars.substring(4, 6), 16);
        return Color.rgb(r, g, b);
    }

    public static boolean isHexColorValueARGBValid(String hexColorValue) {
        if (hexColorValue == null || hexColorValue.length() > 8) {
            return false;
        }
        for (int i = 0; i < hexColorValue.length(); i++) {
            if (!isHexCharacter(hexColorValue.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isHexColorValueRGBValid(String hexColorValue) {
        if (hexColorValue == null || hexColorValue.length() > 6) {
            return false;
        }
        for (int i = 0; i < hexColorValue.length(); i++) {
            if (!isHexCharacter(hexColorValue.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param hexColorValue : voi dieu kien gia tri nay da valid boi method isHexCharacter
     * @return
     */
    public static String convertToHexColorValueARGBChars(String hexColorValue) {
        String copy = hexColorValue;
        if (StringUtils.isBlank(copy)) {
            copy = StringUtils.EMPTY;
        }
        copy += "00000000";
        return copy.substring(0, 8);
    }

    public static String convertToHexColorValueRGBChars(String hexColorValue) {
        String copy = hexColorValue;
        if (StringUtils.isBlank(copy)) {
            copy = StringUtils.EMPTY;
        }
        copy += "000000";
        return copy.substring(0, 6);
    }

    public static boolean isHexCharacter(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        if (c >= 'A' && c <= 'F') {
            return true;
        }
        if (c >= 'a' && c <= 'f') {
            return true;
        }
        return false;
    }
}
