package fr.misoda.contact.common;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;

import fr.misoda.contact.R;
import fr.misoda.contact.view.activity.MainActivity;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseTooltip;

public class TooltipTourGuideHelper {

    private TooltipTourGuideHelper() {

    }

    public static MaterialShowcaseView.Builder createSequenceItem(final Activity mainAct, int idTargetView, String tooltipText, String title, CharSequence content, IShowcaseListener showcaseListener) {
        ShowcaseTooltip tooltip = ShowcaseTooltip.build(mainAct)
                .arrowHeight(30)
                .corner(30)
                .textColor(Color.parseColor("#007686"))
                .textSize(TypedValue.COMPLEX_UNIT_SP, 16)
                .text("<b>" + tooltipText + "</b>");

        int darkGreen = mainAct.getResources().getColor(R.color.dark_green);

        return new MaterialShowcaseView.Builder(mainAct)
                .setTitleText(title)
                .setSkipText(mainAct.getString(R.string.cancel_tourguide))
                .setTarget(mainAct.findViewById(idTargetView))
                .setDismissText(mainAct.getString(R.string.tieptuc))
                .setSkipBtnBackground(darkGreen, Color.BLACK)
                .setDismissBtnBackground(darkGreen, Color.BLACK)
                .setContentText(content)
                .setContentTextColor(mainAct.getResources().getColor(R.color.green))
                .setToolTip(tooltip)
                .setListener(showcaseListener)
                .setShapePadding(10);
    }

}
