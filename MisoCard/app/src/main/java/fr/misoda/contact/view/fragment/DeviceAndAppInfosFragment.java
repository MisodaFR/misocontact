package fr.misoda.contact.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.apache.commons.lang3.StringUtils;

import fr.misoda.contact.BuildConfig;
import fr.misoda.contact.R;
import fr.misoda.contact.common.DateTimeUtil;
import fr.misoda.contact.common.TextUtil;


public class DeviceAndAppInfosFragment extends Fragment {
    public static final String LOG_TAG = DeviceAndAppInfosFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_and_app_infos, container, false);

        TextView textView = view.findViewById(R.id.tv_manufacturer);
        String manufacturer = getString(R.string.manufacturer);
        String twoPoints = getString(R.string.two_points);
        String space = StringUtils.SPACE;
        String label = manufacturer + twoPoints + space;
        String manufacturerValue = Build.MANUFACTURER;
        manufacturerValue = TextUtil.upperCaseFirstLetter(manufacturerValue);
        SpannableStringBuilder spannable = new SpannableStringBuilder(label + manufacturerValue);
        // Color in light and dark theme
        int colorBlueOrange = getResources().getColor(R.color.blue_green);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(colorBlueOrange);
        spannable.setSpan(foregroundColorSpan, label.length() - 1, label.length() + manufacturerValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);

        textView = view.findViewById(R.id.tv_model);
        String model = getString(R.string.model);
        label = model + twoPoints + space;
        String modelValue = Build.MODEL;
        modelValue = TextUtil.upperCaseFirstLetter(modelValue);
        spannable = new SpannableStringBuilder(label + modelValue);
        spannable.setSpan(foregroundColorSpan, label.length() - 1, label.length() + modelValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);

        textView = view.findViewById(R.id.tv_version_android);
        String androidVersion = getString(R.string.android_version_off_device);
        label = androidVersion + twoPoints + space;
        String androidVersionValue = Build.VERSION.RELEASE;
        spannable = new SpannableStringBuilder(label + androidVersionValue);
        spannable.setSpan(foregroundColorSpan, label.length() - 1, label.length() + androidVersionValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);

        textView = view.findViewById(R.id.tv_app_name);
        String appName = getString(R.string.app_name_label);
        label = appName + twoPoints + space;
        String appNameValue = getString(R.string.app_name);
        spannable = new SpannableStringBuilder(label + appNameValue);
        spannable.setSpan(foregroundColorSpan, label.length() - 1, label.length() + appNameValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);

        textView = view.findViewById(R.id.tv_app_type);
        String appType = getString(R.string.app_type);
        label = appType + twoPoints + space;
        String appTypeValue = getString(R.string.free_contain_no_ads);
        spannable = new SpannableStringBuilder(label + appTypeValue);
        spannable.setSpan(foregroundColorSpan, label.length() - 1, label.length() + appTypeValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);

        textView = view.findViewById(R.id.tv_app_version);
        String appVersion = getString(R.string.app_version);
        label = appVersion + twoPoints + space;
        String appVersionValue = BuildConfig.VERSION_NAME;
        spannable = new SpannableStringBuilder(label + appVersionValue);
        spannable.setSpan(foregroundColorSpan, label.length() - 1, label.length() + appVersionValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);

        textView = view.findViewById(R.id.tv_date_update);
        String dateUpdate = getString(R.string.update_date);
        label = dateUpdate + twoPoints + space;
        String dateUpdateValue = DateTimeUtil.getUpdateDate(getActivity());

        spannable = new SpannableStringBuilder(label + dateUpdateValue);
        spannable.setSpan(foregroundColorSpan, label.length() - 1, label.length() + dateUpdateValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable, TextView.BufferType.SPANNABLE);

        return view;
    }
}


