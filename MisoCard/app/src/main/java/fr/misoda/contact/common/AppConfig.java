package fr.misoda.contact.common;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import fr.misoda.contact.view.activity.MainActivity;

public class AppConfig {
    private static final AppConfig instance = new AppConfig();
    private MainActivity mainAct;

    private AppConfig() {
    }

    public synchronized static AppConfig getInstance() {
        return instance;
    }

    public MainActivity getMainAct() {
        return mainAct;
    }

    public void setMainAct(MainActivity mainAct) {
        this.mainAct = mainAct;
    }

    public int getInt(String key, int defautValue) {
        return PreferenceManager.getDefaultSharedPreferences(mainAct).getInt(key, defautValue);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor;
        editor = PreferenceManager.getDefaultSharedPreferences(mainAct).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defautValue) {
        return PreferenceManager.getDefaultSharedPreferences(mainAct).getBoolean(key, defautValue);
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor;
        editor = PreferenceManager.getDefaultSharedPreferences(mainAct).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
