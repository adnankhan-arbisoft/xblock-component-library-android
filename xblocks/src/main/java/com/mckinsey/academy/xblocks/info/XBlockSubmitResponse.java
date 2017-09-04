package com.mckinsey.academy.xblocks.info;

import com.mckinsey.academy.xblocks.common.Constants;

/**
 * Wrapper class to hold submit api response data.
 */

public class XBlockSubmitResponse<T> {

    private T mSubmitResponse;
    @Constants.ResponseStatus
    private String mStatus;
    private String mFeedbackTitle;
    private String mFeedbackMessage;

    public void setSubmitResponse(T submitResponse) {
        mSubmitResponse = submitResponse;
    }

    public T getSubmitResponse() {
        return mSubmitResponse;
    }

    /**
     * {@code true} if the {@link #mStatus} received from API is {@link Constants#STATUS_CORRECT},
     * false otherwise
     *
     * @return true if user answer was correct, false otherwise
     */
    public boolean isCorrect() {
        return Constants.STATUS_CORRECT.equalsIgnoreCase(mStatus);
    }

    /**
     * {@code true} if the {@link #mStatus} received from API is {@link Constants#STATUS_PARTIAL},
     * false otherwise
     *
     * @return true if user answer was partially correct, false otherwise
     */
    public boolean isPartial() {
        return Constants.STATUS_PARTIAL.equalsIgnoreCase(mStatus);
    }

    /**
     * Returns the status received from API. Ideally should be one from
     * {@link com.mckinsey.academy.xblocks.common.Constants.ResponseStatus}
     *
     * @return The status received from API
     */
    @Constants.ResponseStatus
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    /**
     * Returns header label or Title
     * This can be empty in cases. Like for example, if default is "Thank you!"
     *
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
     *
     * @return
     */
    public String getFeedbackMessage() {
        return mFeedbackMessage;
    }

    public void setFeedbackMessage(String mFeedbackMessage) {
        this.mFeedbackMessage = mFeedbackMessage;
    }
}
