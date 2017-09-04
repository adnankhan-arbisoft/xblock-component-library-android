package com.mckinsey.academy.xblocks.info;

/**
 * Generic class to wrap XBlock user answers.
 */
public class XBlockUserAnswer<T> {

    private T mUserAnswer;

    public void set(T userAnswer) {
        mUserAnswer = userAnswer;
    }

    public T get() {
        return mUserAnswer;
    }
}