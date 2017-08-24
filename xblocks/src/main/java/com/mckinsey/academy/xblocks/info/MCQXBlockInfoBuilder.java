package com.mckinsey.academy.xblocks.info;

import com.mckinsey.academy.xblocks.exception.InstantiationException;
import com.mckinsey.academy.xblocks.model.MCQOption;

import java.util.List;

/**
 * MCQXBlockInfo contains all the information required for MCQXBlockComponent. Use
 * {@link MCQXBlockInfoBuilder} to create object of MCQXBlockInfo.
 */
public class MCQXBlockInfoBuilder {

    private String mTitle;
    private String mDetails;
    private String mQuestion;
    private List<MCQOption> arrOptions;
    private boolean isMultiSelectEnable = false;

    /**
     * Set name of the video component here, which will appear below video player.
     *
     * @param title name of the video component.
     * @return MCQXBlockInfoBuilder
     */
    public MCQXBlockInfoBuilder setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * Set detail of the video component here, which will appear below mTitle(below video player).
     *
     * @param detail description of the video component.
     * @return MCQXBlockInfoBuilder
     */
    public MCQXBlockInfoBuilder setDetails(String detail) {
        this.mDetails = detail;
        return this;
    }

    public MCQXBlockInfoBuilder setQuestion(String question) {
        this.mQuestion = question;
        return this;
    }

    public MCQXBlockInfoBuilder setOptions(List<MCQOption> options) {
        this.arrOptions = options;
        return this;
    }

    public MCQXBlockInfoBuilder setMultiSelectEnable(boolean isMultiSelectEnable) {
        this.isMultiSelectEnable = isMultiSelectEnable;
        return this;
    }

    public XBlockInfo build() {
        if (mQuestion == null) {
            throw new InstantiationException("Question has not been set");
        }
        if (arrOptions == null) {
            throw new InstantiationException("Options has not been set");
        }
        if (arrOptions.size() == 0) {
            throw new InstantiationException("Options are not available");
        }
        return new MCQXBlockInfo(mTitle, mDetails, mQuestion, arrOptions, isMultiSelectEnable);
    }
}
