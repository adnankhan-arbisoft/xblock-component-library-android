package com.mckinsey.academy.xblocks.info;

import com.mckinsey.academy.xblocks.common.Constants;

/**
 * Wrapper class to hold submit api response data.
 */

public class XBlockSubmitResponse<T> {

    private T mSubmitResponse;
    @Constants.ResponseStatus
    private String status;
    private String mFeedbackTitle;
    private String mFeedbackMessage;

    public void setSubmitResponse(T submitResponse) {
        mSubmitResponse = submitResponse;
    }

    public T getSubmitResponse() {
        return mSubmitResponse;
    }

    public boolean isSuccess() {
        return Constants.STATUS_CORRECT.equalsIgnoreCase(status);
    }

    public boolean isPartial() {
        return Constants.STATUS_PARTIAL.equalsIgnoreCase(status);
    }

    @Constants.ResponseStatus
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns header label or Title
     * This can be empty in cases. Like for example, if default is "Thanks you!"
     * @return
     */
    public String getFeedbackTitle() {
        return mFeedbackTitle;
    }

    public void setFeedbackTitle(String mFeedbackTitle) {
        this.mFeedbackTitle = mFeedbackTitle;
    }

    /**
     * Returns the feedback message.
     * Could be empty.
     * @return
     */
    public String getFeedbackMessage() {
        return mFeedbackMessage;
    }

    public void setFeedbackMessage(String mFeedbackMessage) {
        this.mFeedbackMessage = mFeedbackMessage;
    }
}
