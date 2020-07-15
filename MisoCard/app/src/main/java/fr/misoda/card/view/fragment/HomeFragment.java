package fr.misoda.card.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        autoFocus = view.findViewById(R.id.switch_auto_focus);
        useFlash = view.findViewById(R.id.switch_use_flash);

        view.findViewById(R.id.img_btn_scan_text).setOnClickListener(view1 -> {
            HomeFragmentDirections.ActionHomeFragmentToScanTextFragment action = HomeFragmentDirections.actionHomeFragmentToScanTextFragment();
            action.setAutoFocus(autoFocus.isChecked());
            action.setUseFlash(useFlash.isChecked());
            NavHostFragment.findNavController(HomeFragment.this).navigate(action);
        });

        setHasOptionsMenu(true);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                NavHostFragment.findNavController(this).navigate(R.id.action_setting_fragment);
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}