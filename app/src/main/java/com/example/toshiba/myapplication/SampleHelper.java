package com.example.toshiba.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

/**
 * Created by jrvansuita on 17/02/17.
 */
public class SampleHelper implements View.OnClickListener {

    private Activity activity;

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
                .addGooglePlayStoreLink("play")
                .addGitHubLink("knatnaela")
                .addBitbucketLink("user")
                .addFacebookLink("natnaeladane.58")
                .addTwitterLink("user")
                .addInstagramLink("natyyxll")
                .addGooglePlusLink("user")
                .addYoutubeChannelLink("NayEntertainment")
                .addDribbbleLink("user")
                .addLinkedInLink("Link")
                .addEmailLink("knatnaelaadane@gmail.com")
                .addWhatsappLink("Naty", "+251923759362")
                .addSkypeLink("user")
                .addGoogleLink("user")
                .addAndroidLink("user")
                .addWebsiteLink("site")
                .addFiveStarsAction()
                .addMoreFromMeAction("Natnael")
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addUpdateAction()
                .setActionsColumnsCount(2)
                .addFeedbackAction("knatnaelaadane@gmail.com")
                .addPrivacyPolicyAction("http://www.docracy.com/2d0kis6uc2")
                .addIntroduceAction((Intent) null)
                .addHelpAction((Intent) null)
                .addChangeLogAction((Intent) null)
                .addRemoveAdsAction((Intent) null)
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
