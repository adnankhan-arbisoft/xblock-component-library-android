package com.mckinsey.academy.xblocks.model;

/**
 * MCQFeedback used to create the result of the selected option.
 * It contains whether the selected option is correct or incorrect and the reason for that
 * as a message.
 */

public class MCQFeedback {

    private boolean isCorrect;
    private String mMessage;

    public MCQFeedback(boolean isCorrect, String message) {
        this.isCorrect = isCorrect;
        this.mMessage = message;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
