package com.mckinsey.academy.xblocks.model;

import android.support.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Model for MCQ and MRQ options used inside
 * {@link com.mckinsey.academy.xblocks.info.MCQXBlockInfo}
 */


public class MCQOption implements Serializable {

    // Define the list of accepted constants and declare the OptionState annotation
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SELECTED, UNSELECTED})
    @interface OptionState {
    }

    // Declare the constants
    public static final int SELECTED = 1;
    public static final int UNSELECTED = 0;

    private String mContent;
    private String mValue;
    @OptionState
    private int mOptionState;

    public MCQOption() {
        mOptionState = UNSELECTED;
    }

    public MCQOption(String content, String value) {
        this.mContent = content;
        this.mValue = value;
        this.mOptionState = UNSELECTED;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    @OptionState
    public int getOptionState() {
        return mOptionState;
    }

    public void setOptionState(@OptionState int optionState) {
        this.mOptionState = optionState;
    }

    public void toggleState() {
        mOptionState = (mOptionState == SELECTED)
                ? UNSELECTED : SELECTED;
    }
}
