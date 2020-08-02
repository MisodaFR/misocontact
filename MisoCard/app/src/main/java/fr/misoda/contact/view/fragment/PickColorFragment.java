package fr.misoda.contact.view.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.madrapps.pikolo.ColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import java.util.Locale;

import fr.misoda.contact.R;
import fr.misoda.contact.common.GraphicUtil;
import top.defaults.colorpicker.ColorPickerView;

public class PickColorFragment extends Fragment {
    public static final String LOG_TAG = PickColorFragment.class.getSimpleName();
    private static final String CURRENT_COLOR = "Current color";
    private static final String HEX_COLOR_VALUE_HSL = "Hex color value HSL";
    private static final String HEX_COLOR_VALUE_RGB = "Hex color value RGB";
    private TextView tvPickedColorValueRGB;
    private int selectedColorValue;
    //private EventBus bus = EventBus.getDefault();
    private boolean colorValueValid = true;
    private TextView tvMsgColorValueInvalid;
    private ColorPicker colorPickerRGB;
    private TextView tvColorValueHSL;
    private ColorPickerView colorPickerHSL;
    private EditText etColorValueRBG;
    private EditText etColorValueHSL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pick_color, container, false);
        tvMsgColorValueInvalid = view.findViewById(R.id.tv_msg_color_value_error);

        RelativeLayout relaLayoutRGB = view.findViewById(R.id.rela_layout_rgb_picker_color);
        LinearLayout linearLayoutHSL = view.findViewById(R.id.linear_layout_hsl);
        LinearLayout linearLayoutHEX = view.findViewById(R.id.linear_layout_hex);
        etColorValueRBG = view.findViewById(R.id.et_color_value_rgb);
        etColorValueHSL = view.findViewById(R.id.et_color_value_argb);
        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_rgb:
                    relaLayoutRGB.setVisibility(View.VISIBLE);
                    linearLayoutHSL.setVisibility(View.GONE);
                    linearLayoutHEX.setVisibility(View.GONE);
                    colorPickerRGB.setColor(selectedColorValue);
                    tvPickedColorValueRGB.getBackground().setColorFilter(selectedColorValue, PorterDuff.Mode.SRC_ATOP);
                    tvPickedColorValueRGB.setText(intColorToHexRGB(selectedColorValue));
                    tvPickedColorValueRGB.setTextColor(GraphicUtil.getForegroundWhiteOrBlack(selectedColorValue));
                    //imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    colorValueValid = true;
                    break;
                case R.id.radio_hsl:
                    relaLayoutRGB.setVisibility(View.GONE);
                    linearLayoutHSL.setVisibility(View.VISIBLE);
                    linearLayoutHEX.setVisibility(View.GONE);
                    colorPickerHSL.setInitialColor(selectedColorValue);
                    tvColorValueHSL.setBackgroundColor(selectedColorValue);
                    tvColorValueHSL.setTextColor(GraphicUtil.getForegroundWhiteOrBlack(selectedColorValue));
                    tvColorValueHSL.setText(intColorToHexARGB(selectedColorValue));
                    //imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    colorValueValid = true;
                    break;
                case R.id.radio_hex:
                    relaLayoutRGB.setVisibility(View.GONE);
                    linearLayoutHSL.setVisibility(View.GONE);
                    linearLayoutHEX.setVisibility(View.VISIBLE);
                    view.findViewById(R.id.view_picked_color_hex).setBackgroundColor(selectedColorValue);
                    etColorValueRBG.setTag(HEX_COLOR_VALUE_RGB);
                    etColorValueRBG.setText(intColorToHexRGB(selectedColorValue));
                    etColorValueRBG.setTag(null);
                    etColorValueHSL.setTag(HEX_COLOR_VALUE_HSL);
                    etColorValueHSL.setText(intColorToHexARGB(selectedColorValue));
                    etColorValueHSL.setTag(null);
                    colorValueValid = true;
                    tvMsgColorValueInvalid.setVisibility(View.GONE);
                    break;
                default:
            }
        });

        Window window = getActivity().getWindow();
        // RGB
        buildViewRGB(window, view);

        // HSL
        buildHSLView(window, view);

        // HEX
        buildHEXView(view);

        return view;
    }

    private void buildViewRGB(Window window, View rootView) {
        tvPickedColorValueRGB = rootView.findViewById(R.id.img_picked_color_value_rgb);
        colorPickerRGB = rootView.findViewById(R.id.color_picker_rgb);
        // Set content width and height
        Display display = window.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int pickColorSize = Math.min(width, height / 2);
        double ratio = 0.9;
        pickColorSize = (int) (ratio * width);
        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);
        if (isLandscape) {
            ratio = 0.6;
            pickColorSize = (int) (ratio * height);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pickColorSize, pickColorSize);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        colorPickerRGB.setLayoutParams(params);
        colorPickerRGB.setColor(selectedColorValue);
        tvPickedColorValueRGB.getBackground().setColorFilter(selectedColorValue, PorterDuff.Mode.SRC_ATOP);
        tvPickedColorValueRGB.setText(intColorToHexRGB(selectedColorValue));
        tvPickedColorValueRGB.setTextColor(GraphicUtil.getForegroundWhiteOrBlack(selectedColorValue));
        tvMsgColorValueInvalid.setVisibility(View.GONE);
        colorPickerRGB.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                selectedColorValue = color;
                tvPickedColorValueRGB.getBackground().setColorFilter(selectedColorValue, PorterDuff.Mode.SRC_ATOP);
                tvPickedColorValueRGB.setText(intColorToHexRGB(selectedColorValue));
                tvPickedColorValueRGB.setTextColor(GraphicUtil.getForegroundWhiteOrBlack(selectedColorValue));
                colorValueValid = true;
            }
        });
    }

    private void buildHSLView(Window window, View rootView) {
        colorPickerHSL = rootView.findViewById(R.id.hsl_color_picker);
        tvColorValueHSL = rootView.findViewById(R.id.tv_color_hsl_hex);
        colorPickerHSL.setInitialColor(selectedColorValue);

        // Set content width and height
        Display display = window.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        double ratio = 0.9;
        int pickColorSize = Math.min((int) (ratio * width), 2 * height / 3);
        pickColorSize = (int) (ratio * width);
        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);
        if (isLandscape) {
            ratio = 0.6;
            pickColorSize = (int) (ratio * height);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pickColorSize, pickColorSize);
        params.gravity = Gravity.CENTER;
        colorPickerHSL.setLayoutParams(params);

        tvColorValueHSL.setBackgroundColor(selectedColorValue);
        tvColorValueHSL.setTextColor(GraphicUtil.getForegroundWhiteOrBlack(selectedColorValue));
        tvColorValueHSL.setText(intColorToHexARGB(selectedColorValue));
        colorPickerHSL.subscribe((color, fromUser, shouldPropagate) -> {
            if (!fromUser) {
                return;
            }
            selectedColorValue = color;
            tvColorValueHSL.setBackgroundColor(selectedColorValue);
            tvColorValueHSL.setTextColor(GraphicUtil.getForegroundWhiteOrBlack(selectedColorValue));
            tvColorValueHSL.setText(intColorToHexARGB(selectedColorValue));
        });
    }

    private void buildHEXView(View rootView) {
        rootView.findViewById(R.id.view_picked_color_hex).setBackgroundColor(selectedColorValue);
        etColorValueRBG.setTag(HEX_COLOR_VALUE_RGB);
        etColorValueRBG.setText(intColorToHexRGB(selectedColorValue));
        etColorValueRBG.setTag(null);
        etColorValueHSL.setTag(HEX_COLOR_VALUE_HSL);
        etColorValueHSL.setText(intColorToHexARGB(selectedColorValue));
        etColorValueHSL.setTag(null);

        etColorValueRBG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etColorValueRBG.getTag() == null) {
                    // Value changed by user
                    String hex = s.toString();
                    Log.d(LOG_TAG, "hex : " + hex);
                    try {
                        selectedColorValue = GraphicUtil.hexColorValueRGBToInt(hex);
                        rootView.findViewById(R.id.view_picked_color_hex).setBackgroundColor(selectedColorValue);
                        colorValueValid = true;
                        tvMsgColorValueInvalid.setVisibility(View.GONE);
                        etColorValueHSL.setTag(HEX_COLOR_VALUE_HSL);
                        etColorValueHSL.setText(intColorToHexARGB(selectedColorValue));
                        etColorValueHSL.setTag(null);
                    } catch (IllegalArgumentException e) {
                        Log.d(LOG_TAG, "Error: " + e.getMessage());
                        e.printStackTrace();
                        colorValueValid = false;
                        tvMsgColorValueInvalid.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Value changed by program
                }
            }
        });

        etColorValueHSL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etColorValueHSL.getTag() == null) {
                    // Value changed by user
                    String hex = s.toString();
                    Log.d(LOG_TAG, "hex : " + hex);
                    try {
                        selectedColorValue = GraphicUtil.hexColorValueARGBToInt(hex);
                        rootView.findViewById(R.id.view_picked_color_hex).setBackgroundColor(selectedColorValue);
                        colorValueValid = true;
                        tvMsgColorValueInvalid.setVisibility(View.GONE);
                        etColorValueRBG.setTag(HEX_COLOR_VALUE_RGB);
                        etColorValueRBG.setText(intColorToHexRGB(selectedColorValue));
                        etColorValueRBG.setTag(null);
                    } catch (IllegalArgumentException e) {
                        Log.d(LOG_TAG, "Error: " + e.getMessage());
                        e.printStackTrace();
                        colorValueValid = false;
                        tvMsgColorValueInvalid.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Value changed by program
                }
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PickColorFragmentArgs args = PickColorFragmentArgs.fromBundle(getArguments());
        int currentColorOfLightTheme = args.getCurrentColorOfLightTheme();
        selectedColorValue = currentColorOfLightTheme;

        view.findViewById(R.id.linear_layout_for_picked_color_value_rgb).getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        view.findViewById(R.id.linear_layout_for_tv_color_hsl).setBackgroundColor(Color.BLACK);

        view.findViewById(R.id.linear_layout_for_view_picked_color_hex).setBackgroundColor(Color.BLACK);
        ((TextView) view.findViewById(R.id.tv_rgb_title)).setTextColor(Color.BLACK);
        ((TextView) view.findViewById(R.id.tv_argb_title)).setTextColor(Color.BLACK);
        etColorValueRBG.setTextColor(Color.BLACK);
        etColorValueHSL.setTextColor(Color.BLACK);
    }

    private String intColorToHexARGB(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "%02X%02X%02X%02X", a, r, g, b);
    }

    private String intColorToHexRGB(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "%02X%02X%02X", r, g, b);
    }
}