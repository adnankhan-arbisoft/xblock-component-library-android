package com.mckinsey.academy.xblocks.callbacks;

import com.mckinsey.academy.xblocks.model.MCQOption;

/**
 * Interface definition for a callback to be invoked when the checked state of an {@link MCQOption}
 * is changed.
 */
public interface MCQXBlockCallback extends Callback {

    /**
     * Called when the state of an {@link MCQOption} has changed.
     *
     * @param position Position of the {@link MCQOption} in passed choices
     * @param option   {@link MCQOption} in consideration
     */
    void onOptionSelectionChanged(int position, MCQOption option);

    /**
     * Null callback to implement Null object pattern
     */
    MCQXBlockCallback NULL_CALLBACK = new MCQXBlockCallback() {
        @Override
        public void onOptionSelectionChanged(int position, MCQOption option) {

        }
    };
}