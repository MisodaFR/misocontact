package fr.misoda.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SaveToContactsFragment extends Fragment {
    // Use a compound button so either checkbox or switch widgets work.
    private TextView textValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_to_contacts, container, false);

        textValue = (TextView) view.findViewById(R.id.tv_text_value);

        view.findViewById(R.id.btn_save_to_contacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save to contact
            }
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textValue.setText(SecondFragment.textResult);
    }
}