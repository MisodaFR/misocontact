package fr.misoda.contact.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.vision.barcode.Barcode;

import org.apache.commons.lang3.StringUtils;

import fr.misoda.contact.BuildConfig;
import fr.misoda.contact.R;
import fr.misoda.contact.common.AppConfig;
import fr.misoda.contact.common.Constant;
import fr.misoda.contact.common.GraphicUtil;
import fr.misoda.contact.view.component.barcode.BarcodeGraphicTracker;

public class MainActivity extends AppCompatActivity implements BarcodeGraphicTracker.BarcodeUpdateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConfig.getInstance().setMainAct(this);
        int theme = AppConfig.getInstance().getInt(Constant.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String subtitle = getString(R.string.version) + StringUtils.SPACE + BuildConfig.VERSION_NAME;
        toolbar.setSubtitle(Html.fromHtml("<small>" + subtitle + "</small>"));

        switch (theme) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                setupLightThemeColors();
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
            default:
        }
    }

    public void setupLightThemeColors() {
        int appBackgroundColor = AppConfig.getInstance().getInt(Constant.CURRENT_COLOR_OF_LIGHT_THEME, Color.BLUE);
        int foregroundColor = GraphicUtil.getForegroundWhiteOrBlack(appBackgroundColor); // black or white
        int lighterOrDarkerColor = GraphicUtil.getLighterOrDarkerColor(appBackgroundColor, 1.3f);
        int foregroundColorOfLighterOrDarker = GraphicUtil.getForegroundWhiteOrBlack(lighterOrDarkerColor); // black or white
        /*
        Neu app background color đủ tối, tức là foregroundColorOfLighterOrDarker = white thi co the set color cho status va navigation bar
        Con neu ko thi set more darker color
        Hien tai chua the set cac icon tren status bar va 3 nut tren navigation co mau tuong phan voi mau nen
        Nen tam dung cach nay
        */
        Window window = getWindow();
        if (foregroundColorOfLighterOrDarker == Color.WHITE) {
            window.setStatusBarColor(lighterOrDarkerColor);
            window.setNavigationBarColor(lighterOrDarkerColor);
        } else {
            int moreDarker = GraphicUtil.toDarkerColor(appBackgroundColor, 1.5f);
            window.setStatusBarColor(moreDarker);
            window.setNavigationBarColor(moreDarker);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(appBackgroundColor);
        toolbar.setTitleTextColor(foregroundColor);
        toolbar.setSubtitleTextColor(foregroundColor);

    }

    @Override
    public void onBarcodeDetected(Barcode barcode) {
        //do something with barcode data returned
    }
}