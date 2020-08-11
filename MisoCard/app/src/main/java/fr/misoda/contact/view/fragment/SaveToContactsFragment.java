package fr.misoda.contact.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Set;

import fr.misoda.contact.R;
import fr.misoda.contact.common.AppConfig;
import fr.misoda.contact.common.Constant;
import fr.misoda.contact.common.ContactHelper;
import fr.misoda.contact.common.TooltipTourGuideHelper;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class SaveToContactsFragment extends Fragment {
    public static final String LOG_TAG = SaveToContactsFragment.class.getSimpleName();
    private static final String SHOWCASE_ID = "Showcase of SaveToContactsFragment";
    private EditText etTextValue;

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
        View view = inflater.inflate(R.layout.fragment_save_to_contacts, container, false);

        etTextValue = view.findViewById(R.id.et_text_value);

        view.findViewById(R.id.btn_save_to_contacts).setOnClickListener(view1 -> {
            // save to contact
            createOrEditAContact(etTextValue.getText().toString());
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, true)) {
            presentShowcaseSequence();
            return;
        }
        String textResult = SaveToContactsFragmentArgs.fromBundle(getArguments()).getScannedText();
        etTextValue.setText(textResult);
    }

    public void createOrEditAContact(String text) {
        Set<String> phoneNumbers = ContactHelper.extractPhoneNumber(text, "FR");
        Log.d(LOG_TAG, "Phone numbers : " + phoneNumbers);

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
                NavController navController = NavHostFragment.findNavController(SaveToContactsFragment.this);
                if (showcaseView.isWasSkipped()) {
                    AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
                    navController.navigate(R.id.toHomeFragment);
                } else {
                    navController.navigate(R.id.action_setting_fragment);
                }
            }
        };

        String content = getString(R.string.you_click_this_btn_to_open_contacts_create_or_edit) + Constant.DOT;
        MaterialShowcaseView sequenceItem = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.btn_save_to_contacts, getString(R.string.button) + " "
                + getString(R.string.open_contacts), getString(R.string.below_is_button) + " " + getString(R.string.open_contacts), content, showcaseListener).build();
        sequence.addSequenceItem(sequenceItem);

        sequence.start();
    }
}