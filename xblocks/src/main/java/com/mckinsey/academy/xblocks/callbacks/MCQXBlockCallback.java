package com.mckinsey.academy.xblocks.callbacks;

import com.mckinsey.academy.xblocks.model.MCQResult;

import java.util.HashMap;

/**
 * Implemented in the {@link com.mckinsey.academy.xblocks.view.MCQXBlockFragment}
 */
public interface MCQXBlockCallback extends Callback {

    void onReceiveResult(HashMap<Integer, MCQResult> resultMap);

    void onRetry();

}
