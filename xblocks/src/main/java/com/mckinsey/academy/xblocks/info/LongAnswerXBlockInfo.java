package com.mckinsey.academy.xblocks.info;

import android.support.v4.app.Fragment;

import com.mckinsey.academy.xblocks.callbacks.Callback;
import com.mckinsey.academy.xblocks.callbacks.LongAnswerXBlockCallback;
import com.mckinsey.academy.xblocks.view.LongAnswerXBlockFragment;

/**
 * Data class for Long-Answer XBlock
 */

public class LongAnswerXBlockInfo extends XBlockInfo<LongAnswerXBlockCallback> {

    private String mTitle;
    private String mDescription;
    private String mUserAnswer;

    LongAnswerXBlockInfo(String title, String details) {
        super(title, details);
        this.mTitle = title;
        this.mDescription = details;
    }

    LongAnswerXBlockInfo(String title, String details, String userAnswer) {
        super(title, details);
        this.mTitle = title;
        this.mDescription = details;
        this.mUserAnswer = userAnswer;
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }

    public String getUserAnswer() {
        return this.mUserAnswer;
    }

    @Override
    public Fragment getViewComponent(LongAnswerXBlockCallback callback) {
        LongAnswerXBlockFragment fragment = LongAnswerXBlockFragment.getInstance(this);
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
