/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import com.teamdev.jxbrowser.chromium.demo.resources.Resources;
import com.teamdev.jxbrowser.chromium.internal.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author TeamDev Ltd.
 */
public class JxBrowserDemo {
	private static ChangeProxyThread cpx ;

    private static void initEnvironment() throws Exception {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "my Browser");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    }

    public static void main(String[] args) throws Exception {
        initEnvironment();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initAndDisplayUI();
            }
        });
    }
    
    private static void initAndDisplayUI() {
        final TabbedPane tabbedPane = new TabbedPane();
        insertTab(tabbedPane, TabFactory.createFirstTab(tabbedPane));
//        insertNewTabButton(tabbedPane);

        JFrame frame = new JFrame("我的浏览器");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @SuppressWarnings("CallToSystemExit")
            @Override
            public void windowClosing(WindowEvent e) {
                tabbedPane.disposeAllTabs();
                System.exit(0);
                if (Environment.isMac()) {
                    System.exit(0);
                }
            }
        });
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(1024, 700);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Resources.getIcon("jxbrowser32x32.png").getImage());
        frame.setVisible(true);
        frame.setAlwaysOnTop(false);
        String input = JOptionPane.showInputDialog(frame, "请输入更换代理的时间间隔。");
        double time = 0;
        if(input == ""||input == null||!isDouble(input)){
        	time = 10;
        }else{
        	time = Double.parseDouble(input);
        }
        cpx = new ChangeProxyThread(time,tabbedPane);
        Thread t = new Thread(cpx);
        t.start();
    }
    
    public static boolean isDouble(String value) {
    	  try {
    	   Double.parseDouble(value);
    	   return true;
    	  } catch (NumberFormatException e) {
    	   return false;
    	  }
    	 }

    private static void insertNewTabButton(final TabbedPane tabbedPane) {
        TabButton button = new TabButton(Resources.getIcon("new-tab.png"), "New tab");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                insertTab(tabbedPane, TabFactory.createTab(tabbedPane));
            }
        });
        tabbedPane.addTabButton(button);
    }

    private static void insertTab(TabbedPane tabbedPane, Tab tab) {
        tabbedPane.addTab(tab);
        tabbedPane.selectTab(tab);
    }
}
