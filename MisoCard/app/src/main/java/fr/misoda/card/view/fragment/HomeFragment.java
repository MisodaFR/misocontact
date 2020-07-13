package fr.misoda.card.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import fr.misoda.card.R;

public class HomeFragment extends Fragment {
    public static final String LOG_TAG = HomeFragment.class.getSimpleName();
    // Use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        autoFocus = view.findViewById(R.id.switch_auto_focus);
        useFlash = view.findViewById(R.id.switch_use_flash);

        view.findViewById(R.id.img_btn_scan_text).setOnClickListener(view1 -> {
            HomeFragmentDirections.ActionHomeFragmentToScanTextFragment action = HomeFragmentDirections.actionHomeFragmentToScanTextFragment();
            action.setAutoFocus(autoFocus.isChecked());
            action.setUseFlash(useFlash.isChecked());
            NavHostFragment.findNavController(HomeFragment.this).navigate(action);
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}