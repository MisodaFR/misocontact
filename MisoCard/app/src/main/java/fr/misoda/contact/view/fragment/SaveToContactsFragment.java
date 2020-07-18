package fr.misoda.contact.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import fr.misoda.contact.common.ContactHelper;
import fr.misoda.contact.common.TooltipTourGuideHelper;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class SaveToContactsFragment extends Fragment {
    private static final String SHOWCASE_ID = "Showcase of SaveToContactsFragment";
    // Use a compound button so either checkbox or switch widgets work.
    private TextView tvTextValue;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_to_contacts, container, false);

        tvTextValue = view.findViewById(R.id.tv_text_value);

        view.findViewById(R.id.btn_save_to_contacts).setOnClickListener(view1 -> {
            // save to contact
            String textResult = SaveToContactsFragmentArgs.fromBundle(getArguments()).getScannedText();
            createOrEditAContact(textResult);
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (AppConfig.getInstance().getBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false)) {
            presentShowcaseSequence();
            return;
        }
        String textResult = SaveToContactsFragmentArgs.fromBundle(getArguments()).getScannedText();
        tvTextValue.setText(textResult);
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
                AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
                NavController navController = NavHostFragment.findNavController(SaveToContactsFragment.this);
                navController.navigate(R.id.toHomeFragment);
            }
        };

        String contentOpenCameraBtn = "Bạn nhấp nút này để mở danh bạ, tạo mới hoặc sửa rồi lưu contact" + Constant.DOT;
        MaterialShowcaseView sequenceItem = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.btn_save_to_contacts, "Nút mở camera", "Here is the open camera button", contentOpenCameraBtn, showcaseListener).build();
        sequence.addSequenceItem(sequenceItem);

        /*IShowcaseListener showcaseListenerForBtnUseFlash = new IShowcaseListener() {
            @Override
            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                //Toast.makeText(getApplicationContext(), "Showcase displayed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                // Neu nguoi dung bam nut Thoat tren huong dan thi phan huong dan se ket thuc va app se chinh thuc hoat dong
                if (showcaseView.isWasSkipped()) {
                    AppConfig.getInstance().setBoolean(Constant.SHOULD_DISPLAY_TOUR_GUIDE_KEY, false);
                    //mainAct.beginApp();
                } else {
                    menu.performIdentifierAction(R.id.action_settings, 0);
                }
            }
        };

        sequenceItem = TooltipTourGuideHelper.createSequenceItem(mainAct, R.id.switch_use_flash, "Nút bật/tắt sử dụng flash", "Here is the rrrrr button", "kkk", showcaseListenerForBtnUseFlash).build();
        sequence.addSequenceItem(sequenceItem);*/

        sequence.start();
    }
}