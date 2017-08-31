package com.mckinsey.academy.xblocks.info;

import android.text.TextUtils;

import com.mckinsey.academy.xblocks.exception.InstantiationException;

/**
 * Data class from Long-Answer XBlock
 */

public class LongAnswerXBlockInfoBuilder {

    private String mTitle;
    private String mDescription;
    private String mUserAnswer;// contains previous user answer if any

    /**
     * Method-Chaining for build object of LongAnswerXBlockBuilder
     * @param title
     * @return
     */
    public LongAnswerXBlockInfoBuilder setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * Method-Chaining for build object of LongAnswerXBlockBuilder
     * @param description
     * @return
     */
    public LongAnswerXBlockInfoBuilder setDescription(String description) {
        this.mDescription = description;
        return this;
    }

    public LongAnswerXBlockInfoBuilder setUserAnswer(String userAnswer) {
        this.mUserAnswer = userAnswer;
        return this;
    }

    /**
     * Returns intance of LongAnswerXBlockInfo composed with title and description.
     * @return
     */
    public LongAnswerXBlockInfo build() {
        if(TextUtils.isEmpty(mTitle)) {
            throw new InstantiationException("Title can't be empty for Long Answer XBlock");
        }

        // TODO Description could be empty so commenting this for now
        if(TextUtils.isEmpty(mDescription)) {
            mDescription = "";
            // throw new InstantiationException("Description can't be empty for Long Answer XBlock");
        }

        if (TextUtils.isEmpty(mUserAnswer)) {
            this.mUserAnswer = "";
        }

        return new LongAnswerXBlockInfo(mTitle, mDescription, mUserAnswer);
    }
}
