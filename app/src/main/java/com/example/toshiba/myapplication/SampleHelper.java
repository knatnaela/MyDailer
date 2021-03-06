package com.example.toshiba.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

/**
 * Created by jrvansuita on 17/02/17.
 */
public class SampleHelper implements View.OnClickListener {

    private final Activity activity;

    private SampleHelper(Activity activity) {
        this.activity = activity;
    }

    public static SampleHelper with(Activity activity) {
        return new SampleHelper(activity);
    }

    public SampleHelper init() {
        int theme = R.style.Theme_AppCompat;
        activity.setTheme(theme);
        return this;
    }

    public void loadAbout() {
        final FrameLayout flHolder = activity.findViewById(R.id.about);

        AboutBuilder builder = AboutBuilder.with(activity)
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .setPhoto(R.mipmap.profile_picture)
                .setCover(R.mipmap.profile_cover)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName("Natnael Adane")
                .setSubTitle("Knatnaelaadane@gmail.com")
                .setLinksColumnsCount(4)
                .setBrief("I'm warmed of mobile technologies. Ideas maker, curious and nature lover.")
                .addGitHubLink("knatnaela")
                .addFacebookLink("natnaeladane.58")
                .addInstagramLink("natyyxll")
                .addEmailLink("knatnaelaadane@gmail.com")
                .addWhatsappLink("Naty", "+251923759362")
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addUpdateAction()
                .setActionsColumnsCount(2)
                .addFeedbackAction("knatnaelaadane@gmail.com")
                .addPrivacyPolicyAction("https://docs.google.com/document/d/18DpbAD0BFm0xepkSxEgav9bF8Cm2icE0IsyR744Ecrg/edit?usp=sharing")
                .addIntroduceAction((Intent) null)
                .addDonateAction(new Intent(Intent.ACTION_CALL).setData(Uri.parse(Uri.parse("tel:"+"*806"+"*0923759362"+"*5")+Uri.encode("#"))))
                .setWrapScrollView(true)
                .setShowAsCard(true);

        AboutView view = builder.build();

        flHolder.addView(view);
    }


    @Override
    public void onClick(View view) {
           }
}
