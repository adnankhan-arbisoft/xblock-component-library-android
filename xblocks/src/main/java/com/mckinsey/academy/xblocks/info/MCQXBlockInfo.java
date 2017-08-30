package com.mckinsey.academy.xblocks.info;

import android.support.v4.app.Fragment;

import com.mckinsey.academy.xblocks.callbacks.Callback;
import com.mckinsey.academy.xblocks.callbacks.MCQXBlockCallback;
import com.mckinsey.academy.xblocks.model.MCQOption;
import com.mckinsey.academy.xblocks.view.MCQXBlockFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * MCQXBlockInfo contains all the information required for MCQXBlockCompenent. Use
 * {@link MCQXBlockInfoBuilder} to create object of VideoXBlockInfo.
 */

public class MCQXBlockInfo extends XBlockInfo<MCQXBlockCallback> {

    private String mQuestion;
    private List<MCQOption> arrOptions;
    private Boolean isMultiSelectEnable;

    /*
    * This constructor is not public, so Doc comment is not added.
    * The parameter question is name of the title of the question component
    * which will appear in the sub menu.
    * The parameter options is the list of the possible options of the mQuestion.
    */
    MCQXBlockInfo(String title, String details, String question, List<MCQOption> options,
                  boolean isMultiSelectEnable) {
        super(title, details);
        this.mQuestion = question;
        this.arrOptions = options;
        this.isMultiSelectEnable = isMultiSelectEnable;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<MCQOption> getOptions() {
        return arrOptions;
    }

    public Boolean isMultiSelectEnable() {
        return isMultiSelectEnable;
    }

    @Override
    public Fragment getViewComponent(MCQXBlockCallback callback) {
        MCQXBlockFragment fragment = MCQXBlockFragment.newInstance(this);
        if (callback != null) {
            fragment.setCallback(callback);
        }
        return fragment;
    }

    @Override
    public Fragment getViewComponent() {
        return getViewComponent(null);
    }
}
