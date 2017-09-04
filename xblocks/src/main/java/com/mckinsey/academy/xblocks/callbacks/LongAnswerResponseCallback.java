package com.mckinsey.academy.xblocks.callbacks;

import com.mckinsey.academy.xblocks.model.LongAnswerFeedback;

/**
 * Interface to handle Long Answer Feedback after submit api response is received
 */

public interface LongAnswerResponseCallback {

    /**
     * Method to get user input.
     * @return
     */
    String getUserInput();

    /**
     * Called after response is received after submission to update UI.
     * @param title
     * @param message
     */
    void onFeedbackReceived(String title, String message, LongAnswerFeedback feedback);

    /**
     * Call this function if the user wants to retry a question, can be used to restart XBlock
     * by resetting all options so user can re-take the test.
     */
    void onRetakeQuiz();
}
