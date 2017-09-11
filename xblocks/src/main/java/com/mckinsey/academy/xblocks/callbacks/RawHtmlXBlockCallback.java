package com.mckinsey.academy.xblocks.callbacks;

import com.mckinsey.academy.xblocks.view.RawHtmlXBlockFragment;

/**
 * Callback interface to notify consumer of XBlock component
 */

public interface RawHtmlXBlockCallback extends Callback {

    void onWebViewSetup();

    void onLoadStudentViewUrl();

    void onError(String errorMessage);

    public static RawHtmlXBlockCallback NULL_CALLBACK = new RawHtmlXBlockCallback() {

        @Override
        public void onWebViewSetup() {
            // TODO nothig required here
        }

        @Override
        public void onLoadStudentViewUrl() {
            // TODO nothing required
        }

        @Override
        public void onError(String errorMessage) {
            // TODO nothing required
        }
    };
}
