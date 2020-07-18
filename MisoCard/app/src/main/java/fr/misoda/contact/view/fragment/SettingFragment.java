package fr.misoda.contact.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import fr.misoda.contact.R;
import fr.misoda.contact.common.AppConfig;
import fr.misoda.contact.common.Constant;

public class SettingFragment extends Fragment {
    private Switch switchDarkTheme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        switchDarkTheme = view.findViewById(R.id.switch_dark_theme);
        switchDarkTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppConfig.getInstance().setInt(Constant.KEY_THEME, AppCompatDelegate.MODE_NIGHT_YES);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppConfig.getInstance().setInt(Constant.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        view.findViewById(R.id.layout_quit_app).setOnClickListener(v -> getActivity().finish());
        view.findViewById(R.id.layout_device_app_infos).setOnClickListener(v -> NavHostFragment.findNavController(SettingFragment.this).navigate(R.id.action_SettingFragment_to_DeviceAndAppInfosFragment));

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int theme = AppConfig.getInstance().getInt(Constant.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);

        switch (theme) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                switchDarkTheme.setChecked(false);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                switchDarkTheme.setChecked(true);
                break;
            default:
        }
    }
}