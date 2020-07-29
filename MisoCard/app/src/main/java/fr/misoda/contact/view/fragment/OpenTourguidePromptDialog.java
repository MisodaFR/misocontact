package fr.misoda.contact.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import org.apache.commons.lang3.StringUtils;

import fr.misoda.contact.R;
import fr.misoda.contact.common.Constant;
import fr.misoda.contact.common.GraphicUtil;

public class OpenTourguidePromptDialog extends DialogFragment {
    public static final String LOG_TAG = OpenTourguidePromptDialog.class.getSimpleName();

    private IBtnClickListener dialogBtnClickListener;


    public static OpenTourguidePromptDialog newInstance() {
        return new OpenTourguidePromptDialog();
    }

    public OpenTourguidePromptDialog() {

    }

    public void setDialogBtnClickListener(IBtnClickListener dialogBtnClickListener) {
        this.dialogBtnClickListener = dialogBtnClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.msg_ok_cancel_remark_dialog, container, false);

        Button btnOk = view.findViewById(R.id.ok_btn);
        btnOk.setText(getString(R.string.accept));

        Button btnCancel = view.findViewById(R.id.cancel_btn);
        btnCancel.setText(getString(R.string.no_accept));

        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(R.string.xem_huongdan_sudung);

        TextView tvContent = view.findViewById(R.id.tv_content_1);
        tvContent.setText(getString(R.string.welcome_to) + " '" + getString(R.string.app_name) + "'");

        tvContent = view.findViewById(R.id.tv_content_2);
        tvContent.setText(R.string.ban_comuon_xem_huongdan_sudung_ngaybaygio_khong);

        btnOk.setOnClickListener(v -> {
            if (dialogBtnClickListener != null) {
                dialogBtnClickListener.onOkBtnClicked();
            }
            dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            if (dialogBtnClickListener != null) {
                dialogBtnClickListener.onCancelBtnClicked();
            }
            dismiss();
        });

        TextView tvRemark = view.findViewById(R.id.tv_remark_content_1);

        StringBuilder remarkContent = new StringBuilder(getString(R.string.huongdan_se_lanluot_gioithieu)).append(Constant.DOT);
        tvRemark.setText(remarkContent.toString());

        remarkContent = new StringBuilder();
        remarkContent.append(getString(R.string.de_chuyensang_phan_gioithieu_tieptheo)).append(StringUtils.SPACE).append("'").append(getString(R.string.tieptuc)).append("'");
        remarkContent.append(Constant.DOT);
        tvRemark = view.findViewById(R.id.tv_remark_content_2);
        tvRemark.setText(remarkContent.toString());

        remarkContent = new StringBuilder();
        remarkContent.append(getString(R.string.de_thoat_huongdan_ban_hay_nhapvao_dongchu)).append(StringUtils.SPACE).append("'").append(getString(R.string.cancel_tourguide)).append("'");
        remarkContent.append(Constant.DOT);
        tvRemark = view.findViewById(R.id.tv_remark_content_3);
        tvRemark.setText(remarkContent.toString());

        remarkContent = new StringBuilder();
        remarkContent.append(getString(R.string.ban_luonluon_cothe_xem_huongdansudung_bangcach)).append(StringUtils.SPACE).append("'").append(getString(R.string.huongdan_sudung)).append("'");
        remarkContent.append(Constant.DOT);
        tvRemark = view.findViewById(R.id.tv_remark_content_4);
        tvRemark.setText(remarkContent.toString());

        dialog.setCanceledOnTouchOutside(false);
        setCancelable(false);

        LinearLayout parent = new LinearLayout(getContext());
        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.VERTICAL);
        int padding = GraphicUtil.dpToPx(1.f, getContext()); // in pixels
        parent.setPadding(padding, padding, padding, padding);

        parent.setBackgroundColor(getResources().getColor(R.color.black_white));
        parent.addView(view);

        return parent;
    }

    public interface IBtnClickListener {
        void onOkBtnClicked();

        void onCancelBtnClicked();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}


