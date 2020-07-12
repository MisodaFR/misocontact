package fr.misoda.card.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fr.misoda.card.common.ContactHelper;
import fr.misoda.card.R;

public class SaveToContactsFragment extends Fragment {
    // Use a compound button so either checkbox or switch widgets work.
    private TextView textValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_to_contacts, container, false);

        textValue = view.findViewById(R.id.tv_text_value);

        view.findViewById(R.id.btn_save_to_contacts).setOnClickListener(view1 -> {
            // save to contact
            createOrEditAContact(SecondFragment.textResult);
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textValue.setText(SecondFragment.textResult);
    }

    public void createOrEditAContact(String text) {
        // Creates a new Intent to insert or edit a contact
        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        // Sets the MIME type
        intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
        // Add code here to insert extended data, if desired

        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, ContactHelper.getEmail(text))
                .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                // Inserts a phone number
                .putExtra(ContactsContract.Intents.Insert.PHONE, ContactHelper.getWorkPhone(text))
                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)

                .putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, ContactHelper.getMobilePhone(text))
                .putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, ContactHelper.getName(text));

        // Sends the Intent with an request ID
        startActivity(intent);
    }
}