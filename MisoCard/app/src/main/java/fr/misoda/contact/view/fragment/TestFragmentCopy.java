package fr.misoda.contact.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fr.misoda.contact.R;

public class TestFragmentCopy extends Fragment {
    // Use a compound button so either checkbox or switch widgets work.
    private TextView tvTextValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_to_contacts, container, false);

        tvTextValue = view.findViewById(R.id.tv_text_value);

        view.findViewById(R.id.btn_save_to_contacts).setOnClickListener(view1 -> {
            // save to contact
            String textResult = SaveToContactsFragmentArgs.fromBundle(getArguments()).getScannedText();
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}