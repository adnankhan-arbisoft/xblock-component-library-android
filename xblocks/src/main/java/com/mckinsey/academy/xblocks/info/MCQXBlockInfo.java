package com.mckinsey.academy.xblocks.info;

import android.support.v4.app.Fragment;

import com.mckinsey.academy.xblocks.callbacks.Callback;
import com.mckinsey.academy.xblocks.model.MCQOption;
import com.mckinsey.academy.xblocks.view.MCQXBlockFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * MCQXBlockInfo contains all the information required for MCQXBlockCompenent. Use
 * {@link MCQXBlockInfoBuilder} to create object of VideoXBlockInfo.
 */

public class MCQXBlockInfo extends XBlockInfo {

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
    public Fragment getViewComponent(Callback callback) {
        MCQXBlockFragment fragment = MCQXBlockFragment.newInstance(this);
        if (callback != null) {
            fragment.setCallback(callback);
        }
        return fragment;
    }

    /***
     * Filter out the {@link MCQOption} who's status is  is SELECTED
     * @return return the list of the Selected Options
     */
    public List<Integer> getSelectedOptions() {
        List<Integer> selectedOptions = new ArrayList<>();
        for (int i = 0; i < arrOptions.size(); i++) {
            if (arrOptions.get(i).getOptionState() == MCQOption.SELECTED) {
                selectedOptions.add(i);
            }
        }
        return selectedOptions;
    }

    /***
     * Reset the state of the each {@link MCQOption} to UNSELECTED to retry the Question
     */
    public void resetOptionState() {
        for (MCQOption option : arrOptions) {
            option.setOptionState(MCQOption.UNSELECTED);
        }
    }

    @Override
    public Fragment getViewComponent() {
        return getViewComponent(null);
    }
}
