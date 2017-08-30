package com.mckinsey.academy.xblocks.info;

import android.support.v4.app.Fragment;

import com.mckinsey.academy.xblocks.callbacks.Callback;
import com.mckinsey.academy.xblocks.callbacks.VideoXBlockCallback;
import com.mckinsey.academy.xblocks.view.VideoXBlockFragment;

/**
 * VideoXBlockInfo contains all the information required for VideoXBlockComponent. Use
 * {@link VideoXBlockInfoBuilder} to create object of VideoXBlockInfo.
 */
public class VideoXBlockInfo extends XBlockInfo<VideoXBlockCallback> {

    private String embedId;
    private String pcode;
    private String domain;

    /*
     * This constructor is not public, so Doc comment is not added.
     * The parameter title is name of the video component which will appear below video player.
     * The parameter details is the description of vide component which will appear below title.
     */
    VideoXBlockInfo(String title, String details, String embedId, String pcode, String domain) {
        super(title, details);
        this.embedId = embedId;
        this.pcode = pcode;
        this.domain = domain;
    }

    public String getEmbedId() {
        return embedId;
    }

    public String getPcode() {
        return pcode;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public Fragment getViewComponent(VideoXBlockCallback callback) {
        VideoXBlockFragment fragment = VideoXBlockFragment.newInstance(this);
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
