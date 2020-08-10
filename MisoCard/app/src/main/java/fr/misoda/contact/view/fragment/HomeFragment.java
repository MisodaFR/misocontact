package fr.misoda.contact.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.apache.commons.lang3.StringUtils;

import fr.misoda.contact.R;
import fr.misoda.contact.common.AppConfig;
import fr.misoda.contact.common.Constant;
import fr.misoda.contact.common.GraphicUtil;
import fr.misoda.contact.common.TooltipTourGuideHelper;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.ShowcaseTooltip;

public class HomeFragment extends Fragment {
    public static final String LOG_TAG = HomeFragment.class.getSimpleName();
    private static final String SHOWCASE_ID = "Showcase of HomeFragment";
    private SwitchCompat switchAutoFocus;
    private SwitchCompat switchUseFlash;
    private Menu menu;
    private RadioButton radioText;
    private RadioButton radioCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, true)) {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        switchAutoFocus = view.findViewById(R.id.switch_auto_focus);
        switchUseFlash = view.findViewById(R.id.switch_use_flash);
        radioText = view.findViewById(R.id.radio_text);
        radioCode = view.findViewById(R.id.radio_code);

        view.findViewById(R.id.img_btn_scan_text).setOnClickListener(view1 -> {
            Log.d(LOG_TAG, "radioText : " + radioText.isChecked() + ", radioCode : " + radioCode.isChecked());

            if (radioText.isChecked()) {
                HomeFragmentDirections.ActionHomeFragmentToScanTextFragment action = HomeFragmentDirections.actionHomeFragmentToScanTextFragment();
                action.setAutoFocus(switchAutoFocus.isChecked());
                action.setUseFlash(switchUseFlash.isChecked());
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            } else {
                HomeFragmentDirections.ActionHomeFragmentToScanCodeFragment action = HomeFragmentDirections.actionHomeFragmentToScanCodeFragment();
                action.setAutoFocus(switchAutoFocus.isChecked());
                action.setUseFlash(switchUseFlash.isChecked());
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });

        setHasOptionsMenu(true);

        switchAutoFocus.setOnCheckedChangeListener((buttonView, isChecked) -> AppConfig.getInstance().setBoolean(Constant.AUTO_FOCUS, isChecked));
        switchUseFlash.setOnCheckedChangeListener((buttonView, isChecked) -> AppConfig.getInstance().setBoolean(Constant.USE_FLASH, isChecked));
        radioText.setOnCheckedChangeListener((buttonView, isChecked) -> AppConfig.getInstance().setBoolean(Constant.SCAN_TEXT, isChecked));

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switchAutoFocus.setChecked(AppConfig.getInstance().getBoolean(Constant.AUTO_FOCUS, true));
        switchUseFlash.setChecked(AppConfig.getInstance().getBoolean(Constant.USE_FLASH, false));
        boolean willScanText = AppConfig.getInstance().getBoolean(Constant.SCAN_TEXT, false);
        radioText.setChecked(willScanText);
        radioCode.setChecked(!willScanText);

        if (AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_PROMPT_DIALOG, true)) {
            displayTourGuidePromptDialog();
        } else if (AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, true)) {
            presentShowcaseSequence();
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;

        int theme = AppConfig.getInstance().getInt(Constant.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
        switch (theme) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                // Thay doi mau cua menu item (co the ap dung cho dark theme)
                GraphicUtil.setupMenuItemsColor(menu, AppConfig.getInstance().getInt(Constant.CURRENT_COLOR_OF_LIGHT_THEME, Color.BLUE));
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
            default:
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                if (AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, true)) {
                    presentTooltipTourguideOfMenuItemSetting();
                } else {
                    NavHostFragment.findNavController(this).navigate(R.id.action_setting_fragment);
                }
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void presentShowcaseSequence() {
        FragmentActivity mainAct = getActivity();
        MaterialShowcaseView.resetSingleUse(mainAct, SHOWCASE_ID);
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        final MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(mainAct, SHOWCASE_ID);
        sequence.setConfig(config);

        IShowcaseListener showcaseListener = new IShowcaseListener() {
            @Override
            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
            }

            @Override
            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                // Neu nguoi dung bam nut Thoat tren huong dan thi phan huong dan se ket thuc va app se chinh thuc hoat dong
                if (showcaseView.isWasSkipped()) {
                    AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
                    NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                    navController.navigate(R.id.toHomeFragment);
                }
            }
        };

        // Btn open camera
        String tooltipText = getString(R.string.button) + " " + getString(R.string.open_camera);
        String title = getString(R.string.below_is_button) + " " + getString(R.string.open_camera);
        String content = getString(R.string.when_you_click_this_button) + ", " + getString(R.string.camera_will_be_opened_next_window) + Constant.DOT;
        MaterialShowcaseView.Builder item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.img_btn_scan_text, tooltipText, title, content, showcaseListener);
        sequence.addSequenceItem(item.build());

        // Radio btn text
        tooltipText = getString(R.string.button) + " " + getString(R.string.turn_on_scan_text);
        title = getString(R.string.below_is_button) + " " + getString(R.string.turn_on_scan_text);
        content = getString(R.string.when_you_click_this_button) + ", " + getString(R.string.scan_text_mode_will_be_activated) +
                ". " +
                getString(R.string.when_the_mode_is_on) + ", " + getString(R.string.camera_will_scan_text_on_second_window) +
                ".";
        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.radio_text, tooltipText, title, content, showcaseListener);
        item.withRectangleShape();
        sequence.addSequenceItem(item.build());

        // Radio btn qr code
        tooltipText = getString(R.string.button) + " " + getString(R.string.turn_on_scan_qr_code);
        title = getString(R.string.below_is_button) + " " + getString(R.string.turn_on_scan_qr_code);
        content = getString(R.string.when_you_click_this_button) + ", " + getString(R.string.scan_qr_mode_will_be_activated) +
                ". " +
                getString(R.string.when_the_mode_is_on) + ", " + getString(R.string.camera_will_scan_qr_on_second_window) +
                ".";
        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.radio_code, tooltipText, title, content, showcaseListener);
        item.withRectangleShape();
        sequence.addSequenceItem(item.build());

        // Switch auto focus
        tooltipText = getString(R.string.button) + " " + getString(R.string.turn_on_off_auto_focus);
        title = getString(R.string.below_is_button) + " " + getString(R.string.turn_on_off_auto_focus);
        content = getString(R.string.when_you_click_this_button) + ", " + getString(R.string.auto_focus_will_be_on_or_off) +
                ". " +
                getString(R.string.when_the_mode_is_on) + ", " + getString(R.string.camera_will_be_auto_focus) +
                ".";
        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.switch_auto_focus, tooltipText, title, content, showcaseListener);
        item.withRectangleShape();
        sequence.addSequenceItem(item.build());

        IShowcaseListener showcaseListenerForBtnUseFlash = new IShowcaseListener() {
            @Override
            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
            }

            @Override
            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                // Neu nguoi dung bam nut Thoat tren huong dan thi phan huong dan se ket thuc va app se chinh thuc hoat dong
                if (showcaseView.isWasSkipped()) {
                    AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
                    NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                    navController.navigate(R.id.toHomeFragment);
                } else {
                    menu.performIdentifierAction(R.id.action_settings, 0);
                }
            }
        };

        tooltipText = getString(R.string.button) + " " + getString(R.string.on_off_use_flash);
        title = getString(R.string.below_is_button) + " " + getString(R.string.on_off_use_flash);
        content = getString(R.string.when_you_click_this_button) + ", " + getString(R.string.mode_use_flash_will_be_on_or_off) +
                ". " +
                getString(R.string.when_the_mode_is_on) + ", " + getString(R.string.flash_is_on_when_scan_text) +
                ".";
        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.switch_use_flash, tooltipText, title, content, showcaseListenerForBtnUseFlash);
        item.withRectangleShape();
        sequence.addSequenceItem(item.build());

        sequence.start();
    }

    public void presentTooltipTourguideOfMenuItemSetting() {
        FragmentActivity mainAct = getActivity();
        int darkGreen = mainAct.getResources().getColor(R.color.dark_green);
        int foregroundWhiteOrBlackOfDarkGreen = Color.BLACK;
        View view = mainAct.findViewById(R.id.action_settings);
        ShowcaseTooltip tooltip = ShowcaseTooltip.build(mainAct)
                .arrowHeight(30)
                .corner(30)
                .textColor(Color.parseColor("#007686"))
                .textSize(TypedValue.COMPLEX_UNIT_SP, 16)
                .text("<b>" + getString(R.string.button_to_open_window) + " '" + getString(R.string.function_setting_info) + "'" + "</b>");

        String titleText = getString(R.string.above_is_button_open_window) + " '" + getString(R.string.function_setting_info) + "'";
        titleText = StringUtils.EMPTY;
        new MaterialShowcaseView.Builder(mainAct)
                .setTitleText(titleText)
                .setTarget(view)
                .setSkipText(R.string.cancel_tourguide)
                .setDismissText(getString(R.string.tieptuc))
                .setSkipBtnBackground(darkGreen, foregroundWhiteOrBlackOfDarkGreen)
                .setDismissBtnBackground(darkGreen, foregroundWhiteOrBlackOfDarkGreen)
                .setContentText(getString(R.string.when_you_click_this_button) + ", " + getString(R.string.ungdung_se_mocuaso) + " '" + getString(R.string.function_setting_info) + "' " +
                        getString(R.string.so_you_can_change_theme_view_tourguide_info_quit_app) + ".")
                .setContentTextColor(mainAct.getResources().getColor(R.color.green))
                .setListener(new IShowcaseListener() {
                    @Override
                    public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                        if (showcaseView.isWasSkipped()) {
                            AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
                            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                            navController.navigate(R.id.toHomeFragment);
                        } else {
                            HomeFragmentDirections.ActionHomeFragmentToScanTextFragment action = HomeFragmentDirections.actionHomeFragmentToScanTextFragment();
                            action.setAutoFocus(switchAutoFocus.isChecked());
                            action.setUseFlash(switchUseFlash.isChecked());
                            NavHostFragment.findNavController(HomeFragment.this).navigate(action);
                        }
                    }
                })
                .setToolTip(tooltip)
                .show();
    }

    private void displayTourGuidePromptDialog() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }

        OpenTourguidePromptDialog dialog = OpenTourguidePromptDialog.newInstance();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        dialog.setDialogBtnClickListener(new OpenTourguidePromptDialog.IBtnClickListener() {
            @Override
            public void onOkBtnClicked() {
                // Open tour guide
                AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_PROMPT_DIALOG, false);
                AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, true);
                presentShowcaseSequence();
            }

            @Override
            public void onCancelBtnClicked() {// Ng dùng đã hiểu, ko cần xem hướng dẫn
                AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_PROMPT_DIALOG, false);
                AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
            }
        });
        dialog.show(fragmentManager, OpenTourguidePromptDialog.LOG_TAG);
    }
}