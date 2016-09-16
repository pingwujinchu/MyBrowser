/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;
import com.teamdev.jxbrowser.chromium.swing.DefaultDownloadHandler;
import com.teamdev.jxbrowser.chromium.swing.DefaultPopupHandler;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.CustomProxyConfig;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.internal.FileUtil;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author TeamDev Ltd.
 */
public final class TabFactory {
    TabbedPane tp;
    public static Tab createFirstTab(TabbedPane tp) {
        return createTab("http://www.bilibili.com/", tp);
    }

    public static Tab createTab(TabbedPane tp) {
        return createTab("about:blank", tp);
    }

    public static Tab createTab(String url,TabbedPane tp) {
//    	String dataDir = FileUtil.createTempDir("chromium-data").getAbsolutePath();
//    	BrowserContextParams contextParams = new BrowserContextParams(dataDir);
//    	// Browser will use custom user's proxy settings.
//        String proxyRules = "http=foo:80;https=foo:80;ftp=foo:80;socks=foo:80";
//        String exceptions = "<local>";  // bypass proxy server for local web pages
//        contextParams.setProxyConfig(new CustomProxyConfig(proxyRules, exceptions));
//
//        // Creates Browser instance with context configured to use specified proxy settings.
//        Browser browser = new Browser(new BrowserContext(contextParams));
        Browser browser = new Browser();
    	BrowserView browserView = new BrowserView(browser);
        TabContent tabContent = new TabContent(browserView);

        browser.setDownloadHandler(new DefaultDownloadHandler(browserView));
        browser.setDialogHandler(new DefaultDialogHandler(browserView));
        browser.setPopupHandler(new DefaultPopupHandler());

        final TabCaption tabCaption = new TabCaption();
        tabCaption.setTitle("about:blank");

        tabContent.addPropertyChangeListener("PageTitleChanged", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                tabCaption.setTitle((String) evt.getNewValue());
            }
        });

        browser.loadURL(url);
        browser.setPopupHandler(new PopupHandler() {
			
			@Override
			public PopupContainer handlePopup(PopupParams arg0) {
				// TODO method stub
				arg0.getParent().loadURL(arg0.getURL());
				tabCaption.setTitle(arg0.getTargetName());
				return null;
			}
		});
        return new Tab(tabCaption, tabContent,browserView);
    }
    
    public static Tab createProxyTab(String url,String title,TabbedPane tp,String dataDir){
		Proxy proxy = ExcelReader.getRandomProxy();
		String ip = proxy.getIp();
		String port = proxy.getPort();
    	BrowserContextParams contextParams = new BrowserContextParams(dataDir);
        String proxyRules = "http="+ip+":"+port+";https="+ip+":"+port+";ftp="+ip+":"+port+";socks="+ip+":"+port;
        String exceptions = "<local>";  // bypass proxy server for local web pages
        contextParams.setProxyConfig(new CustomProxyConfig(proxyRules, exceptions));
	    
    	Browser b = new Browser(new BrowserContext(contextParams));
		BrowserView bv = new BrowserView(b);
		
		TabContent tabContent = new TabContent(bv);
		 final TabCaption tabCaption = new TabCaption();
	        tabCaption.setTitle(title);

	        tabContent.addPropertyChangeListener("PageTitleChanged", new PropertyChangeListener() {
	            public void propertyChange(PropertyChangeEvent evt) {
	                tabCaption.setTitle((String) evt.getNewValue());
	            }
	        });
	        b.loadURL(url);
	        b.setPopupHandler(new PopupHandler() {
				
				@Override
				public PopupContainer handlePopup(PopupParams arg0) {
					// TODO method stub
					arg0.getParent().loadURL(arg0.getURL());
					tabCaption.setTitle(arg0.getTargetName());
					return null;
				}
			});    
	        return new Tab(tabCaption, tabContent, bv);
    }
}
