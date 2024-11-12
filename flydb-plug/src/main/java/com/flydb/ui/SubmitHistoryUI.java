package com.flydb.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.*;

public class SubmitHistoryUI extends SimpleToolWindowPanel {

    private final Project project;
    private final ToolWindow toolWindow;

    public SubmitHistoryUI(Project project, ToolWindow toolWindow) {
        super(false, false);
        this.project = project;
        this.toolWindow = toolWindow;
        initUI();
    }

    private void initUI() {
        JPanel sqlPanel = new JPanel();
        sqlPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        sqlPanel.setBackground(Color.red);

        JPanel msgPanel = new JPanel();
        msgPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        msgPanel.setBackground(Color.white);

        JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panel.setLeftComponent(sqlPanel);
        panel.setRightComponent(msgPanel);
        panel.setResizeWeight(0.5);

        setContent(panel);
    }
}
