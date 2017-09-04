package com.mckinsey.academy.xblocks.common;

import android.support.annotation.StringDef;

/**
 * It contains common constants.
 */
public class Constants {

    public final static String X_BLOCK_TAG = "XBlocks";
    public static final String EXTRA_XBLOCK_INFO = "xblock_info";
    /**
     * Status strings
     */
    public static final String STATUS_INCORRECT = "incorrect";
    public static final String STATUS_CORRECT = "correct";
    public static final String STATUS_PARTIAL = "partial";
    @StringDef({
            STATUS_INCORRECT,
            STATUS_CORRECT,
            STATUS_PARTIAL
    })
    public @interface ResponseStatus{}

    public final static String ERROR_MSG_CALLBACK_NOT_FOUND = "Either wrong FragmentManager is "
            + "passed to XBlock or the caller does not implement Callback Interface.";

}
