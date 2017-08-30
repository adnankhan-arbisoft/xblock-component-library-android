package com.mckinsey.academy.xblocks.callbacks;

/**
 * Callback interface to notify consumer of XBlock Component.
 */

public interface LongAnswerXBlockCallback extends Callback {

    /**
     * Notify consumer if there is any input from user on the Answer Field.
     */
    void onAnswerFieldUpdate(boolean isEmpty);

    /**
     * Notify/Send consumer userAnswer (just in case)
     */
    void onAnswerUpdate(String userAnswer);

    /** Null Object Pattern */
    public static LongAnswerXBlockCallback NULL_CALLBACK = new LongAnswerXBlockCallback() {


        @Override
        public void onAnswerFieldUpdate(boolean isEmpty) {

        }

        @Override
        public void onAnswerUpdate(String userAnswer) {

        }
    };

}
