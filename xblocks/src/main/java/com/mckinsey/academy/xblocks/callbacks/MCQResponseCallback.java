package com.mckinsey.academy.xblocks.callbacks;

import com.mckinsey.academy.xblocks.model.MCQFeedback;
import com.mckinsey.academy.xblocks.model.MCQOption;

import java.util.HashMap;
import java.util.List;

/**
 * Implemented in the {@link com.mckinsey.academy.xblocks.view.MCQXBlockFragment}
 */
public interface MCQResponseCallback {

    /**
     * Method to get all options available for user
     *
     * @return All options shown to the user
     */
    List<MCQOption> getAllOptions();

    /**
     * Called after test evalaution to mark an answer correct or incorrect and to update UI
     * with response / message for user
     *
     * @param resultMap Map of {@link MCQOption#mValue} to message
     */
    void onFeedbackReceived(String message, HashMap<String, MCQFeedback> feedback);

    /**
     * Call this function if the user wants to retry a question, can be used to restart XBlock
     * by resetting all options so user can re-take the test.
     */
    void onRetakeQuiz();
}