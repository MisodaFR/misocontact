package fr.misoda.contact.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import fr.misoda.contact.R;
import fr.misoda.contact.common.AppConfig;
import fr.misoda.contact.common.Constant;
import fr.misoda.contact.common.TooltipTourGuideHelper;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.ShowcaseTooltip;

public class HomeFragment extends Fragment {
    public static final String LOG_TAG = HomeFragment.class.getSimpleName();
    private static final String SHOWCASE_ID = "Showcase of HomeFragment";
    // Use a compound button so either checkbox or switch widgets work.
    private Switch switchAutoFocus;
    private Switch switchUseFlash;
    private Menu menu;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        switchAutoFocus = view.findViewById(R.id.switch_auto_focus);
        switchUseFlash = view.findViewById(R.id.switch_use_flash);

        view.findViewById(R.id.img_btn_scan_text).setOnClickListener(view1 -> {
            HomeFragmentDirections.ActionHomeFragmentToScanTextFragment action = HomeFragmentDirections.actionHomeFragmentToScanTextFragment();
            action.setAutoFocus(switchAutoFocus.isChecked());
            action.setUseFlash(switchUseFlash.isChecked());
            NavHostFragment.findNavController(HomeFragment.this).navigate(action);
        });

        setHasOptionsMenu(true);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false)) {
            presentShowcaseSequence();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                if (AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false)) {
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
                //Toast.makeText(getApplicationContext(), "Showcase displayed", Toast.LENGTH_SHORT).show();
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

        String contentOpenCameraBtn = "Bạn nhấp nút này để mở camera và quét văn bản" + Constant.DOT;
        MaterialShowcaseView.Builder item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.img_btn_scan_text, "Nút mở camera", "Here is the open camera button", contentOpenCameraBtn, showcaseListener);
        sequence.addSequenceItem(item.build());

        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.switch_auto_focus, "Nút bật/tắt tự động lấy nét", "Here is the dfd button", "jkdjf", showcaseListener);
        item.withRectangleShape();
        sequence.addSequenceItem(item.build());

        IShowcaseListener showcaseListenerForBtnUseFlash = new IShowcaseListener() {
            @Override
            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                //Toast.makeText(getApplicationContext(), "Showcase displayed", Toast.LENGTH_SHORT).show();
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

        item = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.switch_use_flash, "Nút bật/tắt sử dụng flash", "Here is the rrrrr button", "kkk", showcaseListenerForBtnUseFlash);
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
                .text("<b>" + "Nut kjdkf" + " '" + "jkjdkf" + "'" + "</b>");

        new MaterialShowcaseView.Builder(mainAct)
                .setTitleText("jkjdfkj" + " '" + "kjdkjf" + "'")
                .setTarget(view)
                .setSkipText(R.string.cancel_tourguide)
                .setDismissText(getString(R.string.tieptuc))
                .setSkipBtnBackground(darkGreen, foregroundWhiteOrBlackOfDarkGreen)
                .setDismissBtnBackground(darkGreen, foregroundWhiteOrBlackOfDarkGreen)
                .setContentText("kjkdjf" + ", " + "jjjj" + " '" + "kkkk" + "'" + ". " +
                        getString(R.string.chedo_manhinh_giup_ungdung) + ". " + getString(R.string.o_chedo_nay_phia_duoi_day_man_hinh_co) + ".")
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
}