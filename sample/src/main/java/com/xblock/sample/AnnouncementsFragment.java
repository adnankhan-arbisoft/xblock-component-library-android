package com.xblock.sample;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Fragment that contains a {@code {@link WebView}} to load Announcements
 */

public class AnnouncementsFragment extends Fragment {

    private static final String TAG = AnnouncementsFragment.class.getSimpleName();

    private WebView mWebView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mWebView = new WebView(getActivity());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            setPermissions(webSettings);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // Following is a sample json from Announcements API
        String announcementJson = "<h2 style=\\\"color:#E37222\\\">Managing emotions | Fred Kofman on difficult conversations</h2>\\n\\n<p>What's the hardest part of challenging conversations for most people? In the next two lessons, we will deep dive into how to manage emotions: first we'll learn how to manage our own emotions, and then move into managing others' emotions.</p>\\n\\n\\n<p><strong><u>Suggested activities: Feb 9-15</u></strong></p>\\n<ol>\\n\\t<li>Complete <a href=\\\"https://www.mckinseyacademy.com/courses/MMP2/MCC/FEB2016/lessons/i4x:%2F%2FMMP2/MCC/chapter/b5f0e11062ba47069e57cada974741e9/module/i4x:%2F%2FMMP2/MCC/vertical/519bf97b65e5431fadef57e373e2a278\\\">Lesson 3: Manage your emotions </a> and <a href=\\\"https://www.mckinseyacademy.com/courses/MMP2/MCC/FEB2016/lessons/i4x:%2F%2FMMP2/MCC/chapter/47d39d58be1a4eb48594c7b9ff6c4dc4/module/i4x:%2F%2FMMP2/MCC/vertical/f8f82860e84b441b841bf09a8ac6e140\\\" target=\\\"_blank\\\">Lesson 4: Sit in the other person's chair</a></li>\\n    <li>If you have not done so already, meet with your group and submit your <em>Kickoff</em> deliverable. Remember that this is an <strong>optional</strong> activity meant to align the team on expectations and a work plan in preparation for the <em>Pulling it all together</em> group work that is due later in the course. Visit the <a href=\\\"https://www.mckinseyacademy.com/courses/MMP2/MCC/FEB2016/group_work\\\" target=\\\"_blank\\\">group work page</a> for more information.</li>\\n</ol>\\n\\n<p><strong><u>In the news</u></strong></p>\\n<img src=\\\"https://gallery.mailchimp.com/4049f26d4de2ad31f99a4575b/images/afeff60c-12b6-4e0f-a96b-79a673ec0519.png\\\" width=\\\"220\\\" align=\\\"right\\\">\\n<p>This week, you'll consider the personal and business benefits of mastering difficult conversations. For a different perspective or extra practice, check out Fred Kofman's video, <a href=\\\"http://leanin.org/education/managing-difficult-conversations/\\\" target=\\\"_blank\\\">\\\"Managing Difficult Conversations\\\"</a> on <em>LeanIn.org</em>. In this lecture, Kofman shares his perspectives on  how to achieve shared goals and remain true to yourself, even when the stakes are high.</p>";

        // Loading the announcement.html template
        String htmlFileString = AssetUtils.getFileContent(getActivity(), "announcements.html");

        // Formatting the sample json for invalid characters and other stuff.
        // replace the PLACEHOLDER with the content of json for announcements
        htmlFileString = htmlFileString.replace("MCKINSEY_PLACEHOLDER", announcementJson);
        // remove consecutive \\n
        htmlFileString = htmlFileString.replace("\\n\\n", "");
        // remove \\n
        htmlFileString = htmlFileString.replace("\\n", "");
        // remove tab character
        htmlFileString = htmlFileString.replace("\\t", "");
        // replace \\\" (tripple backslash and double quotes) with \" (single bakcslash & double quotes) as \\\" does not let images load
        htmlFileString = htmlFileString.replace("\\\"", "\"");

        // the url part "file://android_asset" can be replaced with server url also
        mWebView.loadDataWithBaseURL("file:///android_asset", htmlFileString, "text/html", "UTF-8", null);
        return mWebView;
    }

    public AnnouncementsFragment() {
        // Default constructor
    }

    public static AnnouncementsFragment newInstance() {
        AnnouncementsFragment frag = new AnnouncementsFragment();
        // TODO add any Arguments if needed
        return frag;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setPermissions(WebSettings webSettings) {
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }
}
