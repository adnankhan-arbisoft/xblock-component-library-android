package com.mckinsey.academy.xblocks.callbacks;

import com.mckinsey.academy.xblocks.exception.PlayingException;

/**
 * Implement this callback in your app to get calls from VideoXBlock component.
 */
public interface VideoXBlockCallback extends Callback {

    /**
     *Called when a XBlock Component get opened or started.
     */
    void onInit();

    /**
     * Called when a XBlock Component get completed.
     */
    void onComplete();

    void onProgressChange(int progress);

    void onError(PlayingException e);

    void onPlay();

    void onPause();

    /*implemented Null object pattern https://en.wikipedia.org/wiki/Null_Object_pattern */
    public static VideoXBlockCallback NULL_CALLBACK = new VideoXBlockCallback() {
        @Override
        public void onProgressChange(int progress) {

        }


        @Override
        public void onError(PlayingException e) {

        }

        @Override
        public void onPlay() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onInit() {

        }

        @Override
        public void onComplete() {

        }
    };
}
