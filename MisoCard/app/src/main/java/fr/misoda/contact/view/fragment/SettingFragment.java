package fr.misoda.contact.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import fr.misoda.contact.R;
import fr.misoda.contact.common.AppConfig;
import fr.misoda.contact.common.Constant;
import fr.misoda.contact.common.GraphicUtil;
import fr.misoda.contact.common.TooltipTourGuideHelper;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class SettingFragment extends Fragment {
    private static final String SHOWCASE_ID = "Showcase of SettingFragment";
    private Switch switchDarkTheme;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false)) {
            return;
        }
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // do nothing if in tour guide
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

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
        view.findViewById(R.id.layout_tour_guide).setOnClickListener(v -> {
            AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, true);
            // Comeback to home fragment to show tour guide
            NavHostFragment.findNavController(SettingFragment.this).navigate(R.id.toHomeFragment);
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false)) {
            presentShowcaseSequence();
            return;
        }

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

    public void presentShowcaseSequence() {
        FragmentActivity activity = getActivity();
        FragmentActivity mainAct = activity;
        MaterialShowcaseView.resetSingleUse(mainAct, SHOWCASE_ID);
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        final MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(mainAct, SHOWCASE_ID);
        sequence.setConfig(config);

        String contentOpenCameraBtn = "Bạn nhấp nút này để mở danh bạ, tạo mới hoặc sửa rồi lưu contact" + Constant.DOT;
        MaterialShowcaseView.Builder item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.layout_dark_theme, "Nút mở camera", "Here is the open camera button", contentOpenCameraBtn, new MyIShowcaseListener(R.id.layout_dark_theme));
        item.withRectangleShape();
        item.setPaddingLayoutBtnsDismissSkip(0, 0, 0, 0);
        sequence.addSequenceItem(item.build());

        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.layout_tour_guide, "Nút mở camera", "Here is the open camera button", contentOpenCameraBtn, new MyIShowcaseListener(R.id.layout_tour_guide));
        item.withRectangleShape();
        item.setPaddingLayoutBtnsDismissSkip(0, 0, 0, 0);
        sequence.addSequenceItem(item.build());

        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.layout_device_app_infos, "Nút mở camera", "Here is the open camera button", contentOpenCameraBtn, new MyIShowcaseListener(R.id.layout_device_app_infos));
        item.withRectangleShape();
        item.setPaddingLayoutBtnsDismissSkip(0, 0, 0, 0);
        item.setMarginsTitle(GraphicUtil.dpToPx(0, activity), GraphicUtil.dpToPx(40, activity), 0, 0);
        sequence.addSequenceItem(item.build());

        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.layout_quit_app, "Nút mở camera", "Here is the open camera button", contentOpenCameraBtn, new MyIShowcaseListener(R.id.layout_quit_app));
        item.withRectangleShape();
        item.setMarginsTitle(GraphicUtil.dpToPx(0, activity), GraphicUtil.dpToPx(0, activity), 0, 0);
        item.setPaddingLayoutBtnsDismissSkip(0, 0, 0, GraphicUtil.dpToPx(56, activity));
        sequence.addSequenceItem(item.build());

        sequence.start();
    }

    private class MyIShowcaseListener implements IShowcaseListener {
        private int idTargetView;

        public MyIShowcaseListener(int idTargetView) {
            this.idTargetView = idTargetView;
        }

        @Override
        public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
            //Toast.makeText(getApplicationContext(), "Showcase displayed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
            if (showcaseView.isWasSkipped()) {
                AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
                NavController navController = NavHostFragment.findNavController(SettingFragment.this);
                navController.navigate(R.id.toHomeFragment);
            } else {
                if (idTargetView == R.id.layout_quit_app) {
                    AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
                    NavController navController = NavHostFragment.findNavController(SettingFragment.this);
                    navController.navigate(R.id.toHomeFragment);
                }
            }
        }
    }
}