package fr.misoda.contact.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

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
        return c >= 'a' && c <= 'f';
    }

    public static String intColorToHexARGB(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "%02X%02X%02X%02X", a, r, g, b);
    }

    public static String intColorToHexRGB(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "%02X%02X%02X", r, g, b);
    }

    // factor phai > 1
    public static int getLighterOrDarkerColor(int color, float factor) {
        if (getForegroundWhiteOrBlack(color) == Color.BLACK) {
            return toDarkerColor(color, factor);
        } else {
            return toLighterColor(color, factor);
        }
    }

    // factor phai > 1
    public static int toLighterColor(int color, float factor) {
        float[] hsl = new float[3];
        ColorUtils.colorToHSL(color, hsl);
        if (hsl[2] >= 1) {
            return color; // vi ko the lighter hon duoc nua : do sang chi co gia tri tu 0 den 1
        }
        if (hsl[2] == 0) {
            hsl[2] = 0.1f;
        }
        hsl[2] *= factor;
        if (hsl[2] > 1) {
            hsl[2] = 1;
        }
        return ColorUtils.HSLToColor(hsl);
    }

    // factor phai > 1
    public static int toDarkerColor(int color, float factor) {
        float[] hsl = new float[3];
        ColorUtils.colorToHSL(color, hsl);
        if (hsl[2] <= 0) {
            return color; // vi ko the darker duoc nua
        }
        hsl[2] /= factor;
        if (hsl[2] > 1) {
            hsl[2] = 1;
        }
        return ColorUtils.HSLToColor(hsl);
    }

    public static void setupMenuItemsColor(@NonNull Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                int appBackgroundColor = AppConfig.getInstance().getInt(Constant.CURRENT_COLOR_OF_LIGHT_THEME, Color.BLUE);
                drawable.setColorFilter(GraphicUtil.getForegroundWhiteOrBlack(appBackgroundColor), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }
}
