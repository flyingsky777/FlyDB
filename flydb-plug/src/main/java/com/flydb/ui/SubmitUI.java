package com.flydb.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;

import javax.swing.*;
import java.awt.*;

public class SubmitUI extends SimpleToolWindowPanel {
    private final Project project;
    private final ToolWindow toolWindow;

    public SubmitUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
    }

    private void initUI() {
        // 创建一个垂直分割面板
        JBSplitter  panel = new JBSplitter(JSplitPane.VERTICAL_SPLIT);

        JPanel sqlPanel = new JPanel();
        sqlPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        sqlPanel.setBackground(Color.red);

        JPanel msgPanel = new JPanel();
        msgPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        msgPanel.setBackground(Color.white);

        panel.setFirstComponent(sqlPanel);
        panel.setSecondComponent(msgPanel);
        panel.setProportion(0.9f);


        setContent(panel);
    }

}
