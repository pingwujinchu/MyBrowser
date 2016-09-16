package com.teamdev.jxbrowser.chromium.demo;
/*


 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.CertificateErrorParams;
import com.teamdev.jxbrowser.chromium.CertificatesDialogParams;
import com.teamdev.jxbrowser.chromium.CloseStatus;
import com.teamdev.jxbrowser.chromium.ColorChooserParams;
import com.teamdev.jxbrowser.chromium.DialogHandler;
import com.teamdev.jxbrowser.chromium.DialogParams;
import com.teamdev.jxbrowser.chromium.FileChooserParams;
import com.teamdev.jxbrowser.chromium.LoadHandler;
import com.teamdev.jxbrowser.chromium.LoadParams;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.PromptDialogParams;
import com.teamdev.jxbrowser.chromium.ReloadPostDataParams;
import com.teamdev.jxbrowser.chromium.UnloadDialogParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * This sample demonstrates how to create Browser instance,
 * embed it into Swing BrowserView container, display it in JFrame and
 * navigate to the "www.google.com" web site.
 */
public class BrowserSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.baidu.com");
        browser.setPopupHandler(new PopupHandler() {
			
			@Override
			public PopupContainer handlePopup(PopupParams arg0) {
				// TODO method stub
				arg0.getParent().loadURL(arg0.getURL());
				return null;
			}
		});
    }
}
