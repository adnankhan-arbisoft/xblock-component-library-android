package com.mckinsey.academy.xblocks.info;

import android.support.v4.app.Fragment;

import com.mckinsey.academy.xblocks.callbacks.RawHtmlXBlockCallback;
import com.mckinsey.academy.xblocks.view.RawHtmlXBlockFragment;

/**
 * XBlock information model class
 */

public class RawHtmlXBlockInfo extends XBlockInfo<RawHtmlXBlockCallback> {

    private String mStudentViewUrl;
    private boolean isGraded;
    private String mAuthToken;// the call to courses server does not work without authorization.
    private String mHtml;
    private String mBaseUrl;

    /**
     * Constructor to initialize Info with title and description only
     * @param title
     * @param details
     */
    public RawHtmlXBlockInfo(String title, String details) {
        super(title, details);
    }

    /**
     * Initializes the XBlock info for RawHtml
     * @param title - title as String <b><i>this may not be required for RawHtml</i></b>
     * @param details - description as String <b><i>this may not be required for RawHtml</i></b>
     * @param studentViewUrl - Url to load html, for RawHtml: at times api returns a direct url <b>this requires oauth</b>.
     * @param html - Html <b>(at times api returns html)</b>
     * @param baseUrl - Base Url <b>When we load raw html, this may be required</b>
     * @param mAuthToken - Authorization token: required to access the secure url
     * @param graded - flag to show if XBlock is graded or not
     */
    RawHtmlXBlockInfo(String title, String details, String studentViewUrl, String html, String baseUrl, String mAuthToken, boolean graded) {
        super(title, details);
        this.mBaseUrl = baseUrl;
        this.mStudentViewUrl = studentViewUrl;
        this.mAuthToken = mAuthToken;
        this.mHtml = html;
        this.mBaseUrl = baseUrl;
        this.isGraded = graded;
    }

    public String getStudentViewUrl() {
        return mStudentViewUrl;
    }

    public void setStudentViewUrl(String studentViewUrl) {
        this.mStudentViewUrl = studentViewUrl;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        this.mAuthToken = authToken;
    }

    public String getHtml() {
        return mHtml;
    }

    public RawHtmlXBlockInfo setHtml(String html) {
        this.mHtml = html;
        return this;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public RawHtmlXBlockInfo setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
        return this;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }

    @Override
    public Fragment getViewComponent(RawHtmlXBlockCallback callback) {
        RawHtmlXBlockFragment rawHtmlXBlockFragment = RawHtmlXBlockFragment.newInstance(this);
        if (callback != null) {
            rawHtmlXBlockFragment.setCallback(callback);
        }
        return rawHtmlXBlockFragment;
    }

    @Override
    public Fragment getViewComponent() {
        return getViewComponent(null);
    }
}
