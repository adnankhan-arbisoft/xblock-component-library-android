package com.mckinsey.academy.xblocks.info;

import com.mckinsey.academy.xblocks.exception.InstantiationException;


/**
 * To Create object of {@link VideoXBlockInfo} use this builder.
 */
public class VideoXBlockInfoBuilder {
    private String title;
    private String htmlDescription;
    private String embedId;
    private String pcode;
    private String domain;

    /**
     * Set name of the video component here, which will appear below video player.
     *
     * @param title name of the video component.
     * @return
     */
    public VideoXBlockInfoBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set htmlDescription of the video component here, which will below video player.
     *
     * @param htmlDescription description of the video component.
     * @return
     */
    public VideoXBlockInfoBuilder setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
        return this;
    }

    public VideoXBlockInfoBuilder setEmbedId(String embedId) {
        this.embedId = embedId;
        return this;
    }

    public VideoXBlockInfoBuilder setPcode(String pcode) {
        this.pcode = pcode;
        return this;
    }

    public VideoXBlockInfoBuilder setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public XBlockInfo build() {
        if (pcode == null) {
            throw new InstantiationException("Pcode code has not been set");
        }
        if (embedId == null) {
            throw new InstantiationException("Embed Id has not been set");
        }
        if (domain == null) {
            throw new InstantiationException("domain has not been set");
        }

        return new VideoXBlockInfo(title, htmlDescription, embedId, pcode, domain);
    }
}