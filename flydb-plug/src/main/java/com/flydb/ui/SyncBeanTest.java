package com.flydb.ui;

import cn.hutool.core.util.RandomUtil;

import javax.swing.*;
import java.awt.*;

public class SyncBeanTest extends JFrame {

    public SyncBeanTest() {
        initUI();
    }

    public static void main(String[] args) {
        new SyncBeanTest();
    }


    public String[] getData() {
        String[] strings = new String[20];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = RandomUtil.randomString(10);
        }
        return strings;
    }

    private void initUI() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(new Label("bean"), BorderLayout.WEST);
        titlePanel.add(new Label("mysql"), BorderLayout.EAST);

        JList beanJL = new JList();
        beanJL.setFixedCellWidth(200);
        beanJL.setListData(getData());
        beanJL.setCellRenderer(new MyJcheckBox());
        beanJL.setSelectionModel(new MyListModel());
        JScrollPane leftPanel = new JScrollPane(beanJL);

        JList sqlJL = new JList();
        sqlJL.setFixedCellWidth(200);
        sqlJL.setListData(getData());
        sqlJL.setCellRenderer(new MyJcheckBox());
        sqlJL.setSelectionModel(new MyListModel());
        JScrollPane rightPanel = new JScrollPane(sqlJL);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setResizeWeight(0.5); // 有内容后无效
        splitPane.setDividerSize(1);


        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BorderLayout());
        btnPanel.add(new JButton("刷新"), BorderLayout.WEST);
        btnPanel.add(new JButton("确定"), BorderLayout.EAST);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);//窗口在屏幕中间显示
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

}

