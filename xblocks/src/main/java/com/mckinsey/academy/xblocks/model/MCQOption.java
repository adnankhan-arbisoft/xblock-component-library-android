package com.mckinsey.academy.xblocks.model;

import java.io.Serializable;

/**
 * Model for MCQ and MRQ options used inside
 * {@link com.mckinsey.academy.xblocks.info.MCQXBlockInfo}
 */

public class MCQOption implements Serializable {

    private String mContent;
    private String mValue;
    private boolean isSelected;

    public MCQOption() {
        isSelected = false;
    }

    public MCQOption(String content, String value) {
        mContent = content;
        mValue = value;
        isSelected = false;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean toggleState() {
        isSelected = !isSelected;
        return isSelected;
    }
}