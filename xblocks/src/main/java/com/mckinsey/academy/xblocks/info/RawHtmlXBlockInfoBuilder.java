package com.mckinsey.academy.xblocks.info;

/**
 * Util class to build information to be passed to RawHtml Component
 */
public class RawHtmlXBlockInfoBuilder {

    private String mTitle;
    private String mDescription;
    private String mStudentViewUrl;
    private String mAuthToken;
    private String mHtml;
    private String mBaseUrl;

    public RawHtmlXBlockInfoBuilder() {
    }

    public RawHtmlXBlockInfoBuilder setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public RawHtmlXBlockInfoBuilder setDescription(String description) {
        this.mDescription = description;
        return this;
    }

    public RawHtmlXBlockInfoBuilder setStudentViewUrl(String studentViewUrl) {
        this.mStudentViewUrl = studentViewUrl;
        return this;
    }

    public RawHtmlXBlockInfoBuilder setHtml(String html) {
        this.mHtml = html;
        return this;
    }

    public RawHtmlXBlockInfoBuilder setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
        return this;
    }

    public RawHtmlXBlockInfoBuilder setAuthToken(String authToken) {
        this.mAuthToken = authToken;
        return this;
    }

    public RawHtmlXBlockInfo build() {
        RawHtmlXBlockInfo info = new RawHtmlXBlockInfo(mTitle, mDescription, mStudentViewUrl, mHtml, mBaseUrl, mAuthToken, false);
        return info;
    }
}
