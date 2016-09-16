/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import com.teamdev.jxbrowser.chromium.swing.BrowserView;

/**
 * @author TeamDev Ltd.
 */
public class Tab {

    private final TabCaption caption;
    private  TabContent content;
    private final BrowserView bv;

   
    public Tab(TabCaption caption, TabContent content, BrowserView bv) {
		super();
		this.caption = caption;
		this.content = content;
		this.bv = bv;
	}

	public TabCaption getCaption() {
        return caption;
    }

    public TabContent getContent() {
        return content;
    }

	public BrowserView getBv() {
		return bv;
	}

	public void setContent(TabContent content) {
		this.content = content;
	}
	
    
}
