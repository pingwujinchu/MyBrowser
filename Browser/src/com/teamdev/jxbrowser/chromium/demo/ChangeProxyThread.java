package com.teamdev.jxbrowser.chromium.demo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.CustomProxyConfig;
import com.teamdev.jxbrowser.chromium.HostPortPair;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.internal.FileUtil;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

/**
 * 
 *  @fileName :   com.teamdev.jxbrowser.chromium.demo.ChangeProxyThread.java
 *
 *	@version : 1.0
 *
 * 	@see { }
 *
 *	@author   :   fan
 *
 *	@since : JDK1.4
 *  
 *  Create date  : 2016年9月12日 下午11:01:01
 *  Last modified time :
 *	
 * 	Test or not : No
 *	Check or not: No
 *
 * 	The modifier :
 *	The checker	: 
 *	 
 *  @describe :
 *  ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED 2016
*/

public class ChangeProxyThread implements Runnable{
	List <Tab> tabs;
	static boolean closed =false;
	double time;
	TabbedPane tp;
	
	public ChangeProxyThread(double time, TabbedPane tp) {
		super();
		this.tabs = tp.getTabs();
		this.time = time;
		this.tp = tp;
	}

	@Override
	public void run() {
		// TODO method stub
		    String dataDir = FileUtil.createTempDir("chromium-data").getAbsolutePath();
			while(!closed){
				try {
					Thread.sleep((long) (time*1000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(int i = 0 ; i < tabs.size() ; i++){
					Tab tb = tabs.get(i);
					Browser browser = tb.getBv().getBrowser();
					String old = browser.getURL();
					String oldTitle = browser.getTitle();
					tp.removeTab(tb);
				    Tab tab = TabFactory.createProxyTab(old, oldTitle, tp, dataDir);
				    tp.addTab(tab);
				}
				tp.validate();
				tp.repaint();
				tabs= tp.getTabs();
			}
	}

	public static boolean isClosed() {
		return closed;
	}

	public static void setClosed(boolean closed) {
		ChangeProxyThread.closed = closed;
	}
	
    private static void insertTab(TabbedPane tabbedPane, Tab tab) {
        tabbedPane.addTab(tab);
        tabbedPane.selectTab(tab);
    }
}
