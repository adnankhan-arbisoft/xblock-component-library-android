package com.mckinsey.academy.xblocks.callbacks;

import com.mckinsey.academy.xblocks.info.XBlockSubmitResponse;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;

/**
 * Interface to be implemented by the XBlock components to return user answer
 *
 * @param <T> Generic type of answer that should be returned by the XBlock
 */
public interface XBlockComponentFragment<T> {

    XBlockUserAnswer<T> getUserAnswer();

    void setSubmitResponse(XBlockSubmitResponse xBlockSubmitResponse);

}
