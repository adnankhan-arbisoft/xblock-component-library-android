package com.mckinsey.academy.xblocks.model;

import java.io.Serializable;

/**
 * Model for MCQ and MRQ options used inside
 * {@link com.mckinsey.academy.xblocks.info.MCQXBlockInfo}
 */

public class MCQOption implements Serializable {

    private String mContent;
    private String mValue;
    private OptionState mOptionState;

    public MCQOption() {
        mOptionState = OptionState.UNSELECTED;
    }

    public MCQOption(String content, String value) {
        this.mContent = content;
        this.mValue = value;
        this.mOptionState = OptionState.UNSELECTED;
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

    public OptionState getOptionState() {
        return mOptionState;
    }

    public void setOptionState(OptionState optionState) {
        this.mOptionState = optionState;
    }

    public void toggleState() {
        mOptionState = (mOptionState == OptionState.SELECTED)
                ? OptionState.UNSELECTED : OptionState.SELECTED;
    }
}
