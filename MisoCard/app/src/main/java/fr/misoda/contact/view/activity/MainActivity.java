package fr.misoda.contact.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.vision.barcode.Barcode;

import org.apache.commons.lang3.StringUtils;

import fr.misoda.contact.BuildConfig;
import fr.misoda.contact.R;
import fr.misoda.contact.common.AppConfig;
import fr.misoda.contact.common.Constant;
import fr.misoda.contact.view.component.barcode.BarcodeGraphicTracker;

public class MainActivity extends AppCompatActivity implements BarcodeGraphicTracker.BarcodeUpdateListener{

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

        /*int appBackgroundColor = Color.MAGENTA;
        toolbar.setBackgroundColor(appBackgroundColor);
        getWindow().setStatusBarColor(appBackgroundColor);
        getWindow().setNavigationBarColor(appBackgroundColor);*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        int theme = AppConfig.getInstance().getInt(Constant.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);

        switch (theme) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                //menu.findItem(R.id.action_light_theme).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                //menu.findItem(R.id.action_dark_theme).setChecked(true);
                break;
            default:
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_settings_fragment);
                return true;
            *//*case R.id.action_quit_app:
                finish();
                return true;
            case R.id.action_light_theme:
                item.setChecked(!item.isChecked());
                if (item.isChecked()) {
                    AppConfig.getInstance().setInt(Constant.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            case R.id.action_dark_theme:
                item.setChecked(!item.isChecked());
                if (item.isChecked()) {
                    AppConfig.getInstance().setInt(Constant.KEY_THEME, AppCompatDelegate.MODE_NIGHT_YES);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                return true;*//*
            default:
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBarcodeDetected(Barcode barcode) {
        //do something with barcode data returned
    }
}