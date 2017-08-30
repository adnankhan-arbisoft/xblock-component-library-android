package com.mckinsey.academy.xblocks.info;

import android.support.v4.app.Fragment;

import com.mckinsey.academy.xblocks.callbacks.Callback;

import java.io.Serializable;

/**
 * An XBlockInfo object provides the needed data to XBlock UI component. It should be passed to
 * {@link com.mckinsey.academy.xblocks.XBlock XBlock} to create an XBlock component.
 *
 * @param <C> Generic type of child class of {@link Callback} that is required by the fragment
 */
public abstract class XBlockInfo<C extends Callback> implements Serializable {

    private String title;
    private String details;

    /*
     * This constructor is not public, so Doc comment is not added.
     * The parameter title is name of the video component which will appear below video player.
     * The parameter details is the description of vide component which will appear below title.
     */
    XBlockInfo(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    /**
     * It should return the corresponding XBlock Fragment component based on information provided
     * in this class.
     *
     * @param callback which are required.
     * @return
     */
    public abstract Fragment getViewComponent(C callback);

    /**
     * It should return the corresponding XBlock Fragment component based on information provided
     * in this class.
     */
    public abstract Fragment getViewComponent();
}



