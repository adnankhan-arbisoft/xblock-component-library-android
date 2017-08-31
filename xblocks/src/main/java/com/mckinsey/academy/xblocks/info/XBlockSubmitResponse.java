package com.mckinsey.academy.xblocks.info;

/**
 * Wrapper class to hold submit api response data.
 */

public class XBlockSubmitResponse<T> {

    private T mSubmitResponse;

    private boolean isSuccess;
    private String mFeedbackTitle;
    private String mFeedbackMessage;

    public void setSubmitResponse(T submitResponse) {
        mSubmitResponse = submitResponse;
    }

    public T getSubmitResponse() {
        return mSubmitResponse;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
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
