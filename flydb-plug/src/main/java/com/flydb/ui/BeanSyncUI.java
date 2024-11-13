package com.flydb.ui;

import cn.hutool.core.util.RandomUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.*;

public class BeanSyncUI extends SimpleToolWindowPanel {

    private final Project project;
    private final ToolWindow toolWindow;

    public BeanSyncUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
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

        setContent(panel);
    }
}


class MyListModel extends DefaultListSelectionModel {
    @Override
    public void setSelectionInterval(int index0, int index1) {
        if (super.isSelectedIndex(index0)) {
            super.removeSelectionInterval(index0, index1);
        } else {
            super.addSelectionInterval(index0, index1);
        }
    }
}

class MyJcheckBox extends JCheckBox implements ListCellRenderer {
    public MyJcheckBox() {
        super();
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setText(value.toString());
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        this.setSelected(isSelected);
        return this;
    }
}
